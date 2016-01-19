
'use strict';

/* Services */

var afServices = angular.module('afServices', ['ngResource']);

afServices.factory('albumsService', ['$http', '$location',
    function($http, $location) {
        var albumsService = {};
        albumsService.contextPath = '';

        albumsService.listAlbums = function(host) {

            var url = albumsService.contextPath + '/albums/search';
            var config = {params: {title: 'SUDEN'}};

            return $http.get(url, config).then(function (response) {
                console.log(response);
                return response.data;
            });
        };

        return albumsService;
    }]);

