
'use strict';

/* Services */

var afServices = angular.module('afServices', ['ngResource']);

afServices.factory('albumsService', ['$http', '$location',
    function($http, $location) {
        var albumsService = {contextPath: ''};


        albumsService.listAlbums = function(host) {

            var url = albumsService.contextPath + '/albums/search';
            var config = {
                    params: {title: 'SUDEN'}
                };

            return $http.get(url, config).then(function (response) {
                console.log(response);
                return response.data;
            });
        };


        albumsService.doLogin = function(credentials) {
            var headers = credentials ? {authorization : "Basic "
                    + btoa(credentials.username + ":" + credentials.password)
                } : {};
            var url = albumsService.contextPath + '/user';
            var config = {
                    headers: headers
                };


            return $http.get(url, config).then(function (response) {
                console.log(response);
                return response.data;
            });
        };

        albumsService.doLogout = function() {
            var url = albumsService.contextPath + '/logout';
            var config = {
            };

            return $http.get(url, config).then(function (response) {
                console.log(response);
                return response.data;
            });
        };

        return albumsService;
    }]);

