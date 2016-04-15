'use strict';

describe('Album Finder controllers', function() {

    beforeEach(function(){
        jasmine.addMatchers({
            toEqualData: function() {
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

    beforeEach(module(function($provide) {
        $provide.factory('albumsServiceMock', function($q) {
            var listAlbums = jasmine.createSpy('listAlbums').and.callFake(function() {
                var items = [{data:'someData'}], passPromise = true;

                if (passPromise) {
                    return $q.when(items);
                }
                else {
                    return $q.reject('something went wrong');
                }
            });

            var deleteAlbums = jasmine.createSpy('deleteAlbums').and.callFake(function() {
                    return $q.when({});
            });

            return {
                listAlbums: listAlbums,
                deleteAlbum: deleteAlbums
            };
        });
    }));
    beforeEach(module('albumFinderApp'));



    describe('albumsController', function(){
        var ctrl, $scope, albumsService, $q, $window;

        beforeEach(inject(function(_$rootScope_, $route, _$window_, $location, albumsServiceMock, _$q_, _$controller_, $httpBackend) {
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


        it('should call list albums service', function() {
            var albums = [{data:'someData'}];

            $scope.title = 'someTitle';
            $scope.year = '2016';
            $scope.listAlbums();
            expect(albumsService.listAlbums).toHaveBeenCalledWith('someTitle', '2016');
            expect($scope.albums).toEqualData(albums);
        });

        it('should delete the specified album', function() {
            spyOn($window, 'confirm').and.callFake(function () {
                return true;
            });

            var album = {id: 5};
            $scope.deleteAlbum(album);
            expect(albumsService.deleteAlbum).toHaveBeenCalledWith(5);
        });


        it('should not delete the specified album', function() {
            spyOn($window, 'confirm').and.callFake(function () {
                return false;
            });

            var album = {id: 5};
            $scope.deleteAlbum(album);
            expect(albumsService.deleteAlbum).not.toHaveBeenCalledWith(5);
        });


    });
});
