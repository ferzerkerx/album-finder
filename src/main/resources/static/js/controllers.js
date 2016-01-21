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
        var authenticate = function(credentials, callback) {
            $rootScope.userInfo = {};
            albumsService.doLogin(credentials).then(function(data) {
                $rootScope.userInfo.authenticated = false;
                if (data) {
                    $rootScope.userInfo.authenticated = true;
                    $rootScope.userInfo.isAdmin = data.isAdmin;
                    console.log($rootScope.userInfo);
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
                $rootScope.userInfo = {};
                $location.path("/");
            });
        }
    }]);