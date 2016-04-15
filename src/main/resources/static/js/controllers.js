'use strict';

/* Controllers */

var afControllers = angular.module('afControllers', []);

afControllers.controller('albumsController', ['$scope', '$route', '$window','$location', 'albumsService',
    function ($scope, $route, $window, $location, albumsService) {

        $scope.title = '';
        $scope.year = '';

        $scope.listAlbums = function() {
            albumsService.listAlbums($scope.title, $scope.year).then(function(data) {
                $scope.albums = data;
            });
            //TODO show spinner
        };


        $scope.deleteAlbum = function(album) {
            var shouldDeleteAlbum = $window.confirm("Are you sure you want to delete:"  + album.title + "?");
            if (shouldDeleteAlbum === true) {
                albumsService.deleteAlbum(album.id).then(function() {
                    $route.reload();
                });
                //TODO show spinner
            }
        };


        $scope.saveOrUpdateAlbum = function() {
            if ($scope.selectedArtistForAlbum != undefined) {
                $scope.currentAlbum.artist = $scope.selectedArtistForAlbum.originalObject;
            }

            albumsService.saveOrUpdateAlbum($scope.currentAlbum).then(function() {
                $('#albumModal').modal('hide');
                $route.reload();
            });
            //TODO show spinner

        };


        $scope.editAlbum = function(album) {
            $scope.currentAlbum = album;
            $scope.initialArtistForAlbum = album.artist;
            $('#albumModal').modal('show');
        };

        $scope.createAlbum = function() {
            $scope.currentAlbum = {
                id:0,
                title:'',
                year:new Date().getFullYear().toString(),
                artist:{name:''}
            };

            $scope.selectedArtistForAlbum = {};
            $('#albumModal').modal('show');
            //TODO show spinner
        };

        $scope.remoteUrlRequestFn = function(str){
            return {name: str};
        };


        $scope.listAlbums();
   }]);


afControllers.controller('loginController', ['$rootScope', '$scope', '$location', '$route', 'albumsService',
    function ( $rootScope, $scope, $location, $route, albumsService) {
        $rootScope.getClass = function (path) {
            return ($location.path().substr(0, path.length) === path) ? 'active' : '';
        };

        $rootScope.userInfo = {};
        $scope.credentials = {};

        var authenticate = function(credentials, callback) {

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

        $scope.login = function() {
            authenticate($scope.credentials, function() {
              if ($rootScope.userInfo.authenticated) {
                $scope.error = false;
                $('#loginModal').modal('hide');
                $scope.credentials = {};
                $route.reload();
              }
              else {
                $route.reload();
                $scope.error = true;
              }
            });
        };

        $scope.logout = function() {
            albumsService.doLogout().then(function() {
                $rootScope.userInfo = {};
                $route.reload();
            });
        };

        authenticate();
    }]);


    afControllers.controller('artistsController', ['$scope', '$route', '$window' ,'albumsService',
        function ($scope, $route, $window, albumsService) {

            $scope.searchName = '';

            $scope.listArtists = function() {
                albumsService.listArtistsByName($scope.searchName).then(function(data) {
                    $scope.artists = data;

                });
            };

            $scope.deleteArtist = function(artist) {
                var shouldDeleteArtist = $window.confirm("Are you sure you want to delete:"  + artist.name + "?");
                if (shouldDeleteArtist === true) {
                    albumsService.deleteArtist(artist.id).then(function() {
                        $route.reload();
                    });
                    //TODO show spinner
                }
             };


            $scope.saveOrUpdateArtist = function() {
                albumsService.saveOrUpdateArtist($scope.currentArtist).then(function() {
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
            };

            $scope.listArtists();

       }]);