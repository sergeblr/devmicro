'use strict';

angular.module('cafemenuapp').factory('OrderService', ['$http', '$q', function($http, $q){

    var REST_SERVICE_URI = 'http://localhost:8082/orders'
    var factory = {
        findAllDTO: findAllDTO,
        findOrderById: findOrderById,
        findOrdersDTOByDateTime: findOrdersDTOByDateTime,
        createOrder: createOrder,
        updateOrder: updateOrder,
        deleteOrder: deleteOrder
    };

    return factory;

    function findAllDTO() {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI+"dto")
            .then(
                function (response) {
                    deferred.resolve(response.data);
                    console.debug('service: http.get OK');
                },
                function(errResponse){
                    console.error('Service: Error while fetching OrdersDTO');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function findOrderById(id) {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI+"/"+id)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Service: Error while fetching Order');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function findOrdersDTOByDateTime(startDateTime, endDateTime) {
        var deferred = $q.defer();

        $http.get(REST_SERVICE_URI+"dto/"+startDateTime+"/"+endDateTime)
            .then(
                function (response) {
                    console.debug('Service: OrdersDTO found by date filter');
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.debug('Service: OrdersDTO is not found by date filter');
                    deferred.reject(errResponse.data);
                }
            );
        return deferred.promise;
    }

    function createOrder(order) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI, order)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Service: Error while creating Order');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }


    function updateOrder(order) {
        var deferred = $q.defer();
        $http.put(REST_SERVICE_URI, order)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Service: Error while updating Order');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function deleteOrder(id) {
        var deferred = $q.defer();
        $http.delete(REST_SERVICE_URI+'/'+id)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Service: Error while deleting Order');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

}]);