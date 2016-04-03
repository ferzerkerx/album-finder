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
                $('#loginModal').modal('hide');
                $scope.credentials = {};
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


    afControllers.controller('artistsController', ['$scope', '$route', 'albumsService',
        function ($scope, $route, albumsService) {
            albumsService.listArtists().then(function(data) {
                $scope.artists = data;
            });



            $scope.deleteArtist = function(artist) {
                var shouldDeleteArtist = confirm("Are you sure you want to delete:"  + artist.name + "?");
                if (shouldDeleteArtist === true) {
                    albumsService.deleteArtist(artist.id).then(function(data) {
                        $route.reload();
                    });
                    //TODO show spinner
                }
             };


            $scope.saveOrUpdateArtist = function() {
                albumsService.saveOrUpdateArtist($scope.currentArtist).then(function(data) {
                    $('#artistModal').modal('hide');
                    $route.reload();
                });
                //TODO show spinner

            };


            $scope.editArtist = function(artist) {
                $scope.currentArtist = artist;
                $('#artistModal').modal('show');
            };

            $scope.createArtist = function() {
                $scope.currentArtist = {
                     id:0,
                    name:''
                };

                $('#artistModal').modal('show');
            }

       }]);