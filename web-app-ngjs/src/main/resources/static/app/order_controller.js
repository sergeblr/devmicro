'use strict';

cafemenuapp.controller('OrderController', ['$scope', 'OrderService', 'ItemInOrderService', '$routeParams', 'controllerMode',
        function ($scope, OrderService, ItemInOrderService, $routeParams, controllerMode) {
    $scope.ordersdto = [];
    $scope.isDateFilterExpanded = false;
    $scope.orderId = $routeParams.orderId;
    $scope.order = {orderId: null, employeeId: '', orderDateTime: '', summaryPrice: '', itemsQuantity: ''};

    $scope.submitOrder = submitOrder;
    $scope.deleteOrder = deleteOrder;
    $scope.dateFilterExpandSwitch = dateFilterExpandSwitch;
    $scope.findOrdersDTOByDateTime = findOrdersDTOByDateTime;

    /* Date & Time filter */
    $scope.startDateTime = new Date();
    $scope.startDateTime.setFullYear($scope.startDateTime.getFullYear()-1);
    $scope.startDTPickerOpen = false;
    $scope.endDateTime = new Date();
    $scope.endDateTime.setHours($scope.endDateTime.getHours()+1);
    $scope.endDTPickerOpen = false;
    console.log("###", $scope.startDateTime.toISOString().slice(0, -5));
    function dateFilterExpandSwitch() {
        $scope.isDateFilterExpanded = !$scope.isDateFilterExpanded;
        console.log("filter switched: ", $scope.isDateFilterExpanded);
    }


    /* Detecting Controller Mode */
    if(controllerMode === "edit")
    {
        OrderService.findOrderById($routeParams.orderId).then(
            function (order) {
                $scope.order = order;
            },
            function (errResponse) {
                console.error('findOrderById error');
            }
        );
        findItemInOrderByOrderId($routeParams.orderId);
    }
    else if(controllerMode === "all")
        findOrdersDTOByDateTime($scope.startDateTime, $scope.endDateTime);
    else if(controllerMode === "new")
        {/* */ }
    else {}



    /* Entry point when "Create/Save order" submitted */
    function submitOrder() {
        if ($scope.order.orderId === null) {
            console.debug('Saving New Order', $scope.order);
            createOrder($scope.order);
        } else {
            updateOrder($scope.order);
            console.debug('Order updated with id ', $scope.order.orderId);
        }
    }


            /* Pagination: */
            $scope.pgPerPage = '15';
            /*$scope.pgTotalOrders = $scope.orders.length;*/
            $scope.pgCurrentPage = '1';

            $scope.setOrdersPerPage = function (count) {
                $scope.pgPerPage = count;
                $scope.currentPage = 1;
            }


    function findOrdersDTOByDateTime(startDateTime, endDateTime) {

        OrderService.findOrdersDTOByDateTime(
            new Date(startDateTime).toISOString().slice(0, -5),
            new Date(endDateTime).toISOString().slice(0, -5))
            .then(
                function (ordersdto) {
                    $scope.ordersdto = ordersdto;
                },
                function (errResponse) {
                    console.error('findAllOrdersDTOByDateTime error');
                }
            );
    }

    function createOrder(order) {
        OrderService.createOrder(order)
            .then(
                findOrdersDTOByDateTime($scope.startDateTime, $scope.endDateTime),
                function (errResponse) {
                    console.error('createOrder error');
                }
            );
    }

    function updateOrder(order, id) {
        OrderService.updateOrder(order, id)
            .then(
                findOrdersDTOByDateTime($scope.startDateTime, $scope.endDateTime),
                function (errResponse) {
                    console.error('updateOrder error');
                }
            );
    }

    function deleteOrder(id) {
        if(confirm("Delete order #"+id+" ?"))
        {
            OrderService.deleteOrder(id)
                .then(
                    findOrdersDTOByDateTime($scope.startDateTime, $scope.endDateTime),
                    function (errResponse) {
                        console.error('deleteOrder error');
                    }
                );
        }
    }

    /*ItemInOrder NEXT:*/
            /*$scope.iteminorders = [];*/
            /*$scope.iteminorder = {iioOrderId: null, iioItemId: null, iioItemName: '', iioItemPrice: '', iioItemCount: ''};*/
            console.log("abra: ", $scope.iteminorders);
            $scope.submitItemInOrder = submitItemInOrder;
            $scope.deleteItemInOrder = deleteItemInOrder;
            $scope.findItemInOrderByOrderId = findItemInOrderByOrderId;
            function submitItemInOrder() {
                console.debug('Saving New ItemInOrder', $scope.iteminorder);
                createItemInOrder($scope.iteminorder);
            }

            function findItemInOrderByOrderId(orderId) {
                ItemInOrderService.findItemInOrderByOrderId(orderId)
                    .then(
                        function (iteminorders) {
                            $scope.iteminorders = iteminorders;
                            console.log("abra: ", $scope.iteminorders);
                        },
                        function (errResponse) {
                            console.error('findItemInOrderByOrderId error');
                        }
                    );
            }

            function createItemInOrder(iteminorder) {
                ItemInOrderService.createItemInOrder(iteminorder)
                    .then(
                        function (errResponse) {
                            console.error('createItemInOrder error');
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