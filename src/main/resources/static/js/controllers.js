'use strict';

/* Controllers */

var afControllers = angular.module('afControllers', []);

afControllers.controller('albumsController', ['$scope', 'albumsService',
    function ($scope, albumsService) {
        albumsService.listAlbums().then(function(data) {
            $scope.albums = data;
        });

   }]);


afControllers.controller('loginController', ['$rootScope', '$scope', '$location', 'albumsService',
    function ( $rootScope, $scope, $location, albumsService) {

    $rootScope.userInfo = $rootScope.userInfo || {};

    var authenticate = function(credentials, callback) {
        albumsService.doLogin(credentials).then(function(data) {
            $rootScope.userInfo.authenticated = false;
            if (data.username) {
                $rootScope.userInfo.authenticated = true;
            }
            callback && callback();

        });
      };

    authenticate();
    $scope.credentials = {};
    $scope.login = function() {
        authenticate($scope.credentials, function() {
          if ($rootScope.userInfo.authenticated) {
            $location.path("/");
            $scope.error = false;
          } else {
            $location.path("/login");
            $scope.error = true;
          }
        });
    };

    $scope.logout = function() {
        albumsService.doLogout().then(function(data) {
            $rootScope.userInfo.authenticated = false;
                $location.path("/");
        });
    }


}]);