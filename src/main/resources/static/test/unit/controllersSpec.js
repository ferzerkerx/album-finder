'use strict';

describe('Album Finder controllers', function() {

    beforeEach(function(){
        //this.addMatchers({
        //    toEqualData: function(expected) {
        //        return angular.equals(this.actual, expected);
        //    }
        //});
    });

    beforeEach(module('albumFinderApp'));

    describe('albumsController', function(){
        var ctrl, $scope, albumsService, $q;

        beforeEach(inject(function(_$rootScope_, $route, $location, _albumsService_, _$q_, _$controller_) {
            $q = _$q_;
            albumsService = _albumsService_;
            $scope = _$rootScope_.$new();
            ctrl = _$controller_('albumsController', {
                $scope: $scope,
                $route: $route,
                $location: $location,
                albumsService: albumsService
            });
        }));


        it('should call list albums service', function() {

            var albums = [{albumData: 'someValue'}],
                fakePromise = $q.when().then(function() {return {data:albums}});
            spyOn(albumsService, 'listAlbums').and.returnValue(fakePromise);

            $scope.title = 'someTitle';
            $scope.year = '2016';

            $scope.$apply();
            expect(albumsService.listAlbums).toHaveBeenCalledWith('someTitle', '2016');


            expect($scope.albums).toBe(albums);
        });


    });
});
