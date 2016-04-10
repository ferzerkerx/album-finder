
'use strict';

/* Services */

var afServices = angular.module('afServices', ['ngResource']);

afServices.factory('albumsService', ['$http',
    function($http) {

        var appContext = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));;

        var albumsService = {contextPath: appContext};

        var handleResponse = function(response) {
            //TODO do proper error handling
            console.log(response);
            return response.data.data;
        };

        albumsService.listAlbums = function(title, year) {

            var url = albumsService.contextPath + '/albums/search';
            var config = {
                    params: {
                        title: title,
                        year: year
                    }
                };

            return $http.get(url, config).then(function (response) {
                return handleResponse(response);
            });
        };

        albumsService.listArtistsByName = function(name) {
            var url = albumsService.contextPath + '/artist/search';
            var config = {
                    params: {
                        name: name
                    }
                };

            return $http.get(url, config).then(function (response) {
                return handleResponse(response);
            });
        };


        function updateArtist(artist) {

            var url = albumsService.contextPath + '/admin/artist/' + artist.id;

            return $http.put(url, artist).then(function (response) {
                return handleResponse(response);
            });
        }

        function saveArtist(artist) {
            var url = albumsService.contextPath + '/admin/artist';

            return $http.post(url, artist).then(function (response) {
                return handleResponse(response);
            });
        }

        albumsService.saveOrUpdateArtist = function(artist) {
            var isUpdate = artist.id > 0;

            if (isUpdate) {
                return updateArtist(artist)
            }
            return saveArtist(artist);
        };

        albumsService.deleteArtist = function(id) {
            var url = albumsService.contextPath + '/admin/artist/' + id;
            var config = {

                };

            return $http.delete(url, config).then(function (response) {
                return handleResponse(response);
            });
        };

        function updateAlbum(album) {

            var url = albumsService.contextPath + '/admin/album/' + album.id;

            return $http.put(url, album).then(function (response) {
                return handleResponse(response);
            });
        }

        function saveAlbum(album) {
            var url = albumsService.contextPath + '/admin/artist/' + album.artist.id + '/album/';

            return $http.post(url, album).then(function (response) {
                return handleResponse(response);
            });
        }

        albumsService.saveOrUpdateAlbum = function(album) {
            var isUpdate = album.id > 0;

            if (isUpdate) {
                return updateAlbum(album)
            }
            return saveAlbum(album);
        };

        albumsService.deleteAlbum = function(id) {
            var url = albumsService.contextPath + '/admin/album/' + id;
            var config = {

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

            return $http.get(url).then(function (response) {
                return handleResponse(response);
            });
        };

        return albumsService;
    }]);

