(function() {
    'use strict';

angular.module('ResultsControllers', ['ngResource']);

    angular.module('ResultsControllers')
        .controller('ResultsController',
        ['ResultService', '$http','$route', '$scope', '$routeParams', function (ResultService, $http, $route, $scope, $routeParams) {
            $scope.test = 'lalallalal';
            $scope.newProduct = {};
            $scope.newProduct.price = '';
            $scope.newProduct.quantity = '';
            $scope.newProduct.name = '';
            $scope.newProduct.description = '';
            // $scope.addProduct = function () {
            //     console.log($scope.selectedWarehouse);
            //
            //     $scope.newProduct.warehouse = $scope.selectedWarehouse;
            //     console.log($scope.newProduct);
            //     ResultService.createProduct($scope.newProduct, function(info){
            //         console.log('dodawanie nowego produktu');
            //         //$scope.info = info1;
            //         $route.reload();
            //     });
            // }
            //
            // $scope.deleteProduct = function(product) {
            //     ResultService.deleteProduct({productId: product.id} , function(info) {
            //         console.log(info);
            //     });
            //     $route.reload();
            // }
            //
            // ResultService.getProducts(function (products) {
            //     $scope.productsList = products;
            //     console.log($scope.productsList);
            //
            // });
            //
            // ResultService.getWarehouses(function(warehouses) {
            //     $scope.warehousesList = warehouses;
            //     console.log($scope.warehousesList);
            // });
        }]);

})();
