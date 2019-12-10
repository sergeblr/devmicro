'use strict';

angular.module('cafemenuapp').factory('ItemService', ['$http', '$q', function($http, $q){

    var REST_SERVICE_URI = 'http://localhost:8082/items'
    var factory = {
        findAllItems: findAllItems,
        findItemById: findItemById,
        findItemByName: findItemByName,
        createItem: createItem,
        updateItem: updateItem,
        deleteItem: deleteItem
    };

    return factory;

    function findAllItems() {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                    console.debug('service: http.get OK');
                },
                function(errResponse){
                    console.error('Service: Error while fetching Items');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function findItemById(id) {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI+"/"+id)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Service: Error while fetching Items');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function findItemByName(itemName) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI+"/byname", itemName)
            .then(
                function (response) {
                    console.debug('Service: Item ' + itemName + ' found');
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.debug('Service: Item ' + itemName + ' is not found');
                    deferred.reject(errResponse.data);
                }
            );
        return deferred.promise;
    }

    function createItem(item) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI, item)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Service: Error while creating Item');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }


    function updateItem(item) {
        var deferred = $q.defer();
        $http.put(REST_SERVICE_URI, item)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Service: Error while updating Item');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function deleteItem(id) {
        var deferred = $q.defer();
        $http.delete(REST_SERVICE_URI+'/'+id)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Service: Error while deleting Item');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

}]);