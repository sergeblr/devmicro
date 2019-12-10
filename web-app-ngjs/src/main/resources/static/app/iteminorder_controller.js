/*
'use strict';

cafemenuapp.controller('ItemInOrderController', ['$scope', 'ItemInOrderService', '$routeParams',
        function ($scope, ItemInOrderService) {
    $scope.iteminorders = [];
    $scope.iteminorder = {iioOrderId: null, iioItemId: null, iioItemName: '', iioItemPrice: '', iioItemCount: ''};

    $scope.submitItemInOrder = submitItemInOrder;
    $scope.deleteItemInOrder = deleteItemInOrder;


    function submitItemInOrder() {
            console.debug('Saving New ItemInOrder', $scope.iteminorder);
            createItemInOrder($scope.iteminorder);
    }

    function createItemInOrder(iteminorder) {
        ItemInOrderService.createItemInOrder(iteminorder)
            .then(
                function (errResponse) {
                    console.error('createItemInOrder error');
                }
                );
    }

    function isItemInOrderAlreadyInDb() {
        if($scope.iteminorder.iteminorderName.length > 0 && $scope.iteminorderNameEdited !== $scope.iteminorder.iteminorderName)
        ItemInOrderService.findItemInOrderByName($scope.iteminorder.iteminorderName)
            .then(
                function (iteminorder) {
                    if(iteminorder.iteminorderName === $scope.iteminorder.iteminorderName)
                       $scope.iteminorderForm.iteminorderNameField.$setValidity('iteminorderAlreadyInDbError', false);
                    else $scope.iteminorderForm.iteminorderNameField.$setValidity('iteminorderAlreadyInDbError', true);

                    console.debug('Found iteminorder: ', iteminorder);
                },
                function (errResponse) {
                    console.debug('Error findItemInOrderByName: ', $scope.iteminorder.iteminorderName);
                }
            );
    }

    function deleteItemInOrder(orderId, itemId, itemName) {
        if(confirm("Delete item "+itemName+" from order #"+orderId+" ?"))
        {
            ItemInOrderService.deleteItemInOrder(orderId, itemId)
                .then(
                    function (errResponse) {
                        console.error('deleteItemInOrder error');
                    }
                );
        }
    }


}]);
*
 */
