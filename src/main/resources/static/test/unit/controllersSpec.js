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

            return {
                listAlbums: listAlbums
            };
        });
    }));
    beforeEach(module('albumFinderApp'));



    describe('albumsController', function(){
        var ctrl, $scope, albumsService, $q;

        beforeEach(inject(function(_$rootScope_, $route, $location, albumsServiceMock, _$q_, _$controller_, $httpBackend) {
            $httpBackend.whenGET(/partials.*/).respond(200, '');
            $q = _$q_;
            albumsService = albumsServiceMock;
            $scope = _$rootScope_.$new();
            ctrl = _$controller_('albumsController', {
                $scope: $scope,
                $route: $route,
                $location: $location,
                albumsService: albumsServiceMock
            });
        }));


        it('should call list albums service', function() {
            var albums = [{data:'someData'}];

            $scope.$apply();
            expect(albumsService.listAlbums).toHaveBeenCalledWith('', '');

            $scope.title = 'someTitle';
            $scope.year = '2016';
            $scope.listAlbums();
            expect(albumsService.listAlbums).toHaveBeenCalledWith('someTitle', '2016');
            expect($scope.albums).toEqualData(albums);
        });


    });
});
