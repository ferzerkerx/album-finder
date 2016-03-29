
'use strict';

/* Services */

var afServices = angular.module('afServices', ['ngResource']);

afServices.factory('albumsService', ['$http', '$location',
    function($http, $location) {
        var albumsService = {contextPath: ''};

        var handleResponse = function(response) {
            console.log(response);
            return response.data.data;
        };

        albumsService.listAlbums = function() {

            var url = albumsService.contextPath + '/albums/search';
            var config = {
                    params: {title: 'SUDEN'}
                };

            return $http.get(url, config).then(function (response) {
                return handleResponse(response);
            });
        };

         albumsService.listArtists = function() {
            var url = albumsService.contextPath + '/artists';
            var config = {
                };

            return $http.get(url, config).then(function (response) {
                return handleResponse(response);
            });
        };

        albumsService.deleteArtist = function(id) {
            var url = albumsService.contextPath + '/admin/artist/' + id;
            var config = {
                    data: {_method: "DELETE"}
                };

            return $http.delete(url, config).then(function (response) {
                return handleResponse(response);
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
                if (!response.data) {
                    return;
                }
                var data = response.data;
                var isAdmin = false;
                if (data.authorities) {
                    for (var i = 0; i < data.authorities.length; i++ ) {
                        if (data.authorities[i]['authority'] === "ROLE_ADMIN") {
                            isAdmin =  true;
                            break;
                        }
                    }
                }
                return {
                    username: data.username,
                    isAdmin: isAdmin
                };
            });
        };

        albumsService.doLogout = function() {
            var url = albumsService.contextPath + '/logout';
            var config = {
            };

            return $http.get(url, config).then(function (response) {
                return handleResponse(response);
            });
        };

        return albumsService;
    }]);

