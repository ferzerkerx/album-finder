'use strict';

describe('Album Finder controllers', function () {

    beforeEach(function () {
        jasmine.addMatchers({
            toEqualData: function () {
                return {
                    compare: function (actual, expected) {
                        return {
                            pass: angular.equals(actual, expected)
                        };
                    }
                };
            }
        });
    });

    beforeEach(module(function ($provide) {
        $provide.factory('albumsServiceMock', function ($q) {
            var listAlbums = jasmine.createSpy('listAlbums').and.callFake(function () {
                var items = [{data: 'someData'}], passPromise = true;

                if (passPromise) {
                    return $q.when(items);
                }
                else {
                    return $q.reject('something went wrong');
                }
            });

            var deleteAlbums = jasmine.createSpy('deleteAlbums').and.callFake(function () {
                return $q.when({});
            });

            var saveOrUpdateAlbum = jasmine.createSpy('saveOrUpdateAlbum').and.callFake(function () {
                return $q.when({});
            });

            var listArtistsByName = jasmine.createSpy('listArtists').and.callFake(function () {
                var items = [{data: 'someData'}];
                return $q.when(items);
            });

            var deleteArtist = jasmine.createSpy('deleteArtist').and.callFake(function () {
                return $q.when({});
            });

            var saveOrUpdateArtist = jasmine.createSpy('saveOrUpdateArtist').and.callFake(function () {
                return $q.when({});
            });

            var doLogin = jasmine.createSpy('doLogin').and.callFake(function () {
                return $q.when({
                    isAdmin: false
                });
            });

            var doLogout = jasmine.createSpy('doLogout').and.callFake(function () {
                return $q.when({});
            });

            return {
                listAlbums: listAlbums,
                deleteAlbum: deleteAlbums,
                saveOrUpdateAlbum: saveOrUpdateAlbum,
                listArtistsByName: listArtistsByName,
                deleteArtist: deleteArtist,
                saveOrUpdateArtist: saveOrUpdateArtist,
                doLogin: doLogin,
                doLogout: doLogout
            };
        });
    }));
    beforeEach(module('albumFinderApp'));


    describe('albumsController', function () {
        var ctrl, $scope, albumsService, $q, $window;

        beforeEach(inject(function (_$rootScope_, $route, _$window_, $location, albumsServiceMock, _$q_, _$controller_, $httpBackend) {
            $httpBackend.whenGET(/partials.*/).respond(200, '');
            $q = _$q_;
            $window = _$window_;
            albumsService = albumsServiceMock;
            $scope = _$rootScope_.$new();
            ctrl = _$controller_('albumsController', {
                $scope: $scope,
                $route: $route,
                $window: $window,
                $location: $location,
                albumsService: albumsServiceMock
            });

            $scope.$apply();
            expect(albumsService.listAlbums).toHaveBeenCalledWith('', '');
        }));


        it('should list albums based on specified title and year', function () {
            var albums = [{data: 'someData'}];

            $scope.title = 'someTitle';
            $scope.year = '2016';
            $scope.$apply();

            $scope.listAlbums();
            expect(albumsService.listAlbums).toHaveBeenCalledWith('someTitle', '2016');
            expect($scope.albums).toEqualData(albums);
        });

        it('should delete the specified album after confirmation', function () {
            spyOn($window, 'confirm').and.callFake(function () {
                return true;
            });

            var album = {id: 5};
            $scope.deleteAlbum(album);
            expect(albumsService.deleteAlbum).toHaveBeenCalledWith(5);
        });

        it('should not delete the specified album after negative confirmation', function () {
            spyOn($window, 'confirm').and.callFake(function () {
                return false;
            });

            var album = {id: 5};
            $scope.deleteAlbum(album);
            expect(albumsService.deleteAlbum).not.toHaveBeenCalledWith(5);
        });

        it('should save album', function () {
            var album = {id: 5};
            $scope.currentAlbum = album;

            $scope.$apply();
            $scope.saveOrUpdateAlbum();
            expect(albumsService.saveOrUpdateAlbum).toHaveBeenCalledWith(album);
        });

        it('should update album', function () {
            var album = {id: 5},
                artist = {id: 10};
            $scope.currentAlbum = album;
            $scope.selectedArtistForAlbum = {originalObject: artist};

            $scope.$apply();
            $scope.saveOrUpdateAlbum();
            expect(albumsService.saveOrUpdateAlbum).toHaveBeenCalledWith(album);
            expect(album.artist).toBe(artist);
        });

        it('should start editing album', function () {
            var artist = {
                    id: 10
                },
                album = {
                    id: 5,
                    artist: artist
                };

            expect($scope.currentAlbum).toBe(undefined);
            expect($scope.initialArtistForAlbum).toBe(undefined);

            $scope.editAlbum(album);

            expect($scope.currentAlbum).toBe(album);
            expect($scope.initialArtistForAlbum).toBe(artist);
        });


        it('should start creating album', function () {
            expect($scope.selectedArtistForAlbum).toBe(undefined);
            expect($scope.currentAlbum).toBe(undefined);

            $scope.createAlbum();

            expect($scope.selectedArtistForAlbum).not.toBe(undefined);
            expect($scope.currentAlbum).not.toBe(undefined);
        });

        it('should return search string for querying albums', function () {
            var searchString = 'someAlbum',
                urlMap = $scope.remoteUrlRequestFn(searchString);

            expect(urlMap).not.toBe(undefined);
            expect(urlMap.name).toBe(searchString);
        });


    });

    describe('artistsController', function () {
        var ctrl, $scope, albumsService, $q, $window;

        beforeEach(inject(function (_$rootScope_, $route, _$window_, $location, albumsServiceMock, _$q_, _$controller_, $httpBackend) {
            $httpBackend.whenGET(/partials.*/).respond(200, '');
            $q = _$q_;
            $window = _$window_;
            albumsService = albumsServiceMock;
            $scope = _$rootScope_.$new();
            ctrl = _$controller_('artistsController', {
                $scope: $scope,
                $route: $route,
                $window: $window,
                albumsService: albumsServiceMock
            });

            $scope.$apply();
            expect(albumsService.listArtistsByName).toHaveBeenCalledWith('');
        }));


        it('should delete the specified artist after confirmation', function () {
            spyOn($window, 'confirm').and.callFake(function () {
                return true;
            });

            var artist = {id: 5};
            $scope.deleteArtist(artist);
            expect(albumsService.deleteArtist).toHaveBeenCalledWith(5);
        });

        it('should not delete the specified artist after negative confirmation', function () {
            spyOn($window, 'confirm').and.callFake(function () {
                return false;
            });

            var artist = {id: 5};
            $scope.deleteArtist(artist);
            expect(albumsService.deleteArtist).not.toHaveBeenCalledWith(5);
        });

        it('should save or update artist', function () {
            var artist = {id: 5};
            $scope.currentArtist = artist;
            $scope.saveOrUpdateArtist(artist);
            expect(albumsService.saveOrUpdateArtist).toHaveBeenCalledWith(artist);
        });

        it('should start editing artist', function () {
            var artist = {id: 5};
            expect($scope.currentArtist).toBe(undefined);
            $scope.editArtist(artist);
            expect($scope.currentArtist).toBe(artist);
        });

        it('should start creating artist', function () {
            expect($scope.currentArtist).toBe(undefined);
            $scope.createArtist();
            expect($scope.currentArtist).not.toBe(undefined);
        });

    });


    describe('loginController', function () {
        var ctrl, $scope, albumsService, $q, $window, $rootScope;

        beforeEach(inject(function (_$rootScope_, $route, _$window_, $location, albumsServiceMock, _$q_, _$controller_, $httpBackend) {
            $httpBackend.whenGET(/partials.*/).respond(200, '');
            $q = _$q_;
            $rootScope = _$rootScope_;
            $window = _$window_;
            albumsService = albumsServiceMock;
            $scope = $rootScope.$new();
            ctrl = _$controller_('loginController', {
                $rootScope: $rootScope,
                $scope: $scope,
                $location: $location,
                $route: $route,
                $window: $window,
                albumsService: albumsServiceMock
            });

            $scope.$apply();
            expect(albumsService.doLogin).toHaveBeenCalledWith(undefined);
        }));


        it('should login', function () {
            $scope.credentials = {
                username: 'user',
                password: 'password'
            };

            $scope.login();

            expect(albumsService.doLogin).toHaveBeenCalledWith($scope.credentials);
            expect($rootScope.userInfo.authenticated).toBe(true);
            expect($rootScope.userInfo.isAdmin).toBe(false);

            $scope.$apply();
            expect($scope.error).toBe(false);
            expect($scope.credentials).toEqualData({});
        });

        it('should logout', function () {
            $rootScope.userInfo = {
              data: 'someData'
            };

            $scope.$apply();
            $scope.logout();

            expect(albumsService.doLogout).toHaveBeenCalledWith();
            $scope.$apply();
            expect($rootScope.userInfo).toEqualData({});
        });

    });
});
