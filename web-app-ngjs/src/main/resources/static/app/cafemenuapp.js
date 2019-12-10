'use strict';

var cafemenuapp = angular.module('cafemenuapp', [
    'ngRoute',
    'ngMessages',
    'ui.bootstrap',
    'ui.bootstrap.datetimepicker']);

cafemenuapp.config(
    function ($routeProvider, $locationProvider) {

        $routeProvider
            .when('/ordersdto', {
                templateUrl: 'ordersdto.html',
                controller: 'OrderController',
                resolve: {
                    controllerMode: function($route) { return "all"; }
                }
            })
            .when('/order', {
                templateUrl: 'order.html',
                controller: 'OrderController',
                resolve: {
                    controllerMode: function($route) { return "new"; }
                }
            })
            .when('/order/:orderId', {
                templateUrl: 'order.html',
                controller: 'OrderController',
                resolve: {
                    controllerMode: function($route) { return "edit"; }
                }
            })
            .when('/items', {
                templateUrl: 'items.html',
                controller: 'ItemController',
                resolve: {
                    controllerMode: function($route) { return "all"; }
                }
            })
            .when('/item', {
                templateUrl: 'item.html',
                controller: 'ItemController',
                resolve: {
                    controllerMode: function($route) { return "new"; }
                }
            })
            .when('/item/:itemId', {
                templateUrl: 'item.html',
                controller: 'ItemController',
                resolve: {
                    controllerMode: function($route) { return "edit"; }
                }
            })
            .when('/', {
                templateUrl: 'mainpage.html'
            })
            .when('/error', {
                templateUrl: 'exception.html'
            })
            .otherwise({
                templateUrl: 'exception.html'
            });

        $locationProvider.hashPrefix('');
    }
);