'use strict';


var albumFinderApp = angular.module('albumFinderApp', [
    'ngRoute',
    'afControllers',
    'afServices'
]);

albumFinderApp.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
        when('/login', {
            templateUrl: 'partials/login.html',
            controller: 'loginController'
        }).
        when('/albums', {
            templateUrl: 'partials/albums.html',
            controller: 'albumsController'
        }).
        otherwise({
            redirectTo: '/albums'
        });

//        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

    }]);
