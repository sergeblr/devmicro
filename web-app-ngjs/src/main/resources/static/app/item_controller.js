'use strict';

cafemenuapp.controller('ItemController', ['$scope', 'ItemService', '$routeParams', 'controllerMode',
        '$window',
        function ($scope, ItemService, $routeParams, controllerMode, $window) {
    $scope.items = [];
    $scope.itemNameEdited = '';

    $scope.submit = submit;
    $scope.isItemAlreadyInDb = isItemAlreadyInDb;


    /* Detecting Controller Mode */
    if(controllerMode === "edit")
    {
        ItemService.findItemById($routeParams.itemId).then(
            function (item) {
                $scope.item = item;
                $scope.itemNameEdited = item.itemName;
            },
            function (errResponse) {
                console.error('findItemById error');
            }
        );
    }
    else $scope.item = {itemId: null, itemName: '', itemPrice: ''};
    if(controllerMode === "all") findAllItems();
    else if(controllerMode === "new") {};


    /* Entry point when "Create/Save item" submitted */
    function submit() {
        if ($scope.item.itemId === null) {
            console.debug('Saving New Item', $scope.item);
            createItem($scope.item);
        } else {
            updateItem($scope.item);
            console.debug('Item updated with id ', $scope.item.itemId);
        }
        $window.location.href='#items'
    }


            /* Pagination: */
            $scope.pgPerPage = '15';
            /*$scope.pgTotalItems = $scope.items.length;*/
            $scope.pgCurrentPage = '1';

            $scope.setItemsPerPage = function(count) {
                $scope.pgPerPage = count;
                $scope.currentPage = 1;
            }

    /*Check is item already in DB*/
    function isItemAlreadyInDb() {
        if($scope.item.itemName.length > 0 && $scope.itemNameEdited !== $scope.item.itemName)
        ItemService.findItemByName($scope.item.itemName)
            .then(
                function (item) {
                    if(item.itemName === $scope.item.itemName)
                       $scope.itemForm.itemNameField.$setValidity('itemAlreadyInDbError', false);
                    else $scope.itemForm.itemNameField.$setValidity('itemAlreadyInDbError', true);

                    console.debug('Found item: ', item);
                },
                function (errResponse) {
                    console.debug('Error findItemByName: ', $scope.item.itemName);
                }
            );
    }


    function findAllItems() {
        ItemService.findAllItems()
            .then(
                function (items) {
                    $scope.items = items;
                    $scope.pgTotalItems = $scope.items.length;
                },
                function (errResponse) {
                    console.error('findAllItems error');
                }
            );
    }

    function createItem(item) {
        ItemService.createItem(item)
            .then(
                findAllItems,
                function (errResponse) {
                    console.error('createItem error');
                }
            );
    }

    function updateItem(item, id) {
        ItemService.updateItem(item, id)
            .then(
                findAllItems,
                function (errResponse) {
                    console.error('updateItem error');
                }
            );
    }

    $scope.deleteItem = function deleteItem(id, name) {
        if(confirm("Delete item "+name+"?"))
        {
            ItemService.deleteItem(id)
                .then(
                    findAllItems,
                    function (errResponse) {
                        console.error('deleteItem error');
                    }
                );
        }
    }


}]);