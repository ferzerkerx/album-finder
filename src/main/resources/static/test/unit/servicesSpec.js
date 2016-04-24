'use strict';

describe('Album Finder Services', function () {

    beforeEach(module('afServices'));
    describe('albumsService', function () {
        var $albumsService, $httpBackend;

        beforeEach(inject(function (_$location_, _$httpBackend_, albumsService) {
            $httpBackend = _$httpBackend_;
            $albumsService = albumsService;
        }));

        it('should list albums based on specified title and year', function () {
            var title = 'someTitle',
                year = 2016,
                result = '',
                returnData = {data: 'data'},
                returnedPromise;

            $httpBackend.expectGET('/albums/search?title=someTitle&year=2016').respond(returnData);

            returnedPromise = $albumsService.listAlbums(title, year);
            returnedPromise.then(function (response) {
                result = response;
            });

            $httpBackend.flush();
            expect(result).toEqual(returnData.data);
        });

        it('should list albums by artist name', function () {
            var name = 'someName',
                result = '',
                returnData = {data: 'data'},
                returnedPromise;

            $httpBackend.expectGET('/artist/search?name=someName').respond(returnData);

            returnedPromise = $albumsService.listArtistsByName(name);
            result = '';
            returnedPromise.then(function (response) {
                result = response;
            });

            $httpBackend.flush();
            expect(result).toEqual(returnData.data);
        });

        it('should update artist', function () {
            var artist = {
                    id: 3,
                    name: 'someName'
                },
                result = '',
                returnData = {data: 'data'},
                returnedPromise;

            $httpBackend.expectPUT('/admin/artist/3').respond(returnData);

            returnedPromise = $albumsService.saveOrUpdateArtist(artist);
            result = '';
            returnedPromise.then(function (response) {
                result = response;
            });

            $httpBackend.flush();
            expect(result).toEqual(returnData.data);
        });

        it('should save artist', function () {
            var artist = {
                    id: 0,
                    name: 'someName'
                },
                result = '',
                returnData = {data: 'data'},
                returnedPromise;

            $httpBackend.expectPOST('/admin/artist').respond(returnData);

            returnedPromise = $albumsService.saveOrUpdateArtist(artist);
            result = '';
            returnedPromise.then(function (response) {
                result = response;
            });

            $httpBackend.flush();
            expect(result).toEqual(returnData.data);
        });


        it('should delete artist', function () {
            var artist = {
                    id: 5,
                    name: 'someName'
                },
                result = '',
                returnData = {data: 'data'},
                returnedPromise;

            $httpBackend.expectDELETE('/admin/artist/5').respond(returnData);

            returnedPromise = $albumsService.deleteArtist(artist.id);
            result = '';
            returnedPromise.then(function (response) {
                result = response;
            });

            $httpBackend.flush();
            expect(result).toEqual(returnData.data);
        });


        it('should save album', function () {
            var artist = {id: 3},
                album = {
                    id: 0,
                    title: 'someTitle',
                    artist: artist
                },
                result = '',
                returnData = {data: 'data'},
                returnedPromise;

            $httpBackend.expectPOST('/admin/artist/3/album/').respond(returnData);

            returnedPromise = $albumsService.saveOrUpdateAlbum(album);
            result = '';
            returnedPromise.then(function (response) {
                result = response;
            });

            $httpBackend.flush();
            expect(result).toEqual(returnData.data);
        });

        it('should update album', function () {
            var artist = {id: 3},
                album = {
                    id: 4,
                    title: 'someTitle',
                    artist: artist
                },
                result = '',
                returnData = {data: 'data'},
                returnedPromise;

            $httpBackend.expectPUT('/admin/album/4').respond(returnData);

            returnedPromise = $albumsService.saveOrUpdateAlbum(album);
            result = '';
            returnedPromise.then(function (response) {
                result = response;
            });

            $httpBackend.flush();
            expect(result).toEqual(returnData.data);
        });

        it('should delete album', function () {
            var artist = {id: 3},
                album = {
                    id: 4,
                    title: 'someTitle',
                    artist: artist
                },
                result = '',
                returnData = {data: 'data'},
                returnedPromise;

            $httpBackend.expectDELETE('/admin/album/4').respond(returnData);

            returnedPromise = $albumsService.deleteAlbum(album.id);
            result = '';
            returnedPromise.then(function (response) {
                result = response;
            });

            $httpBackend.flush();
            expect(result).toEqual(returnData.data);
        });


        it('should logout', function () {
            var result = '',
                returnData = {data: 'data'},
                returnedPromise;

            $httpBackend.expectGET('/logout').respond(returnData);

            returnedPromise = $albumsService.doLogout();
            result = '';
            returnedPromise.then(function (response) {
                result = response;
            });

            $httpBackend.flush();
            expect(result).toEqual(returnData.data);
        });

        it('should login', function () {
            var credentials = {
                    username: 'user',
                    password: 'secret'
                },
                result = '',
                returnData = {username: 'user', isAdmin: false},
                returnedPromise;

            $httpBackend.expectGET('/user').respond(returnData);

            returnedPromise = $albumsService.doLogin(credentials);
            result = '';
            returnedPromise.then(function (response) {
                result = response;
            });

            $httpBackend.flush();
            expect(result).toEqual(returnData);
        });
    });
});
