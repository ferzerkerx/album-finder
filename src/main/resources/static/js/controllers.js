'use strict';

/* Controllers */

var afControllers = angular.module('afControllers', []);

afControllers.controller('albumsController', ['$scope', 'albumsService',
    function ($scope, albumsService) {
        albumsService.listAlbums().then(function(data) {
            $scope.albums = data;
        });

   }]);


afControllers.controller('loginController', ['$rootScope', '$scope', '$http', '$location', 'albumsService',
    function ( $rootScope, $scope, $http, $location, albumsService) {

    $rootScope.authenticated = false;

//TODO fer move login logic to service
        var authenticate = function(credentials, callback) {

            var headers = credentials ? {authorization : "Basic "
                + btoa(credentials.username + ":" + credentials.password)
            } : {};

            console.log(credentials);
            console.log(headers);
            $http.get('user', {headers : headers}).success(function(data) {
              if (data.username) {
              console.log(data);
                $rootScope.authenticated = true;
              } else {
                $rootScope.authenticated = false;
              }
              callback && callback();
            }).error(function() {
              $rootScope.authenticated = false;
              callback && callback();
            });

          }

        authenticate();
        $scope.credentials = {};
        $scope.login = function() {
            authenticate($scope.credentials, function() {
              if ($rootScope.authenticated) {
                $location.path("/");
                $scope.error = false;
              } else {
                $location.path("/login");
                $scope.error = true;
              }
            });
        };

        $scope.logout = function() {
          $http.get('/logout', {}).success(function() {
            $rootScope.authenticated = false;
            $location.path("/");
          }).error(function(data) {
            $rootScope.authenticated = false;
          });
        }

   }]);