'use strict';

angular.module('cafemenuapp').factory('ItemInOrderService', ['$http', '$q', function($http, $q){

    var REST_SERVICE_URI = 'http://localhost:8082/iteminorders'
    var factory = {
        findItemInOrderByOrderId: findItemInOrderByOrderId,
        createItemInOrder: createItemInOrder,
        deleteItemInOrder: deleteItemInOrder
    };

    return factory;

    function findItemInOrderByOrderId(id) {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI+"/"+id)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Service: Error while fetching ItemInOrders');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function createItemInOrder(itemInOrder) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI, itemInOrder)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Service: Error while creating ItemInOrder');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function deleteItemInOrder(orderId, itemId) {
        var deferred = $q.defer();
        $http.delete(REST_SERVICE_URI+'/'+orderId+'/'+itemId)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Service: Error while deleting ItemInOrder');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

}]);