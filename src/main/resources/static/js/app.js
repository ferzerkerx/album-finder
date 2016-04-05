'use strict';


var albumFinderApp = angular.module('albumFinderApp', [
    'ngRoute',
    'afControllers',
    'afServices'
]);

albumFinderApp.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
        when('/albums', {
            templateUrl: 'partials/albums.html',
            controller: 'albumsController'
        }).
        when('/artists', {
            templateUrl: 'partials/artists.html',
            controller: 'artistsController'
        }).
        otherwise({
            redirectTo: '/albums'
        });
    }]);
