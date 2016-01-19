'use strict';

/* Controllers */

var afControllers = angular.module('afControllers', []);

afControllers.controller('albumsController', ['$scope', 'albumsService',
    function ($scope, albumsService, Storage) {
        albumsService.listAlbums().then(function(data) {
            $scope.albums = data;
        });

   }]);