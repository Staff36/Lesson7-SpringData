angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8080';

    $scope.fillTable = function (page) {
        $http({
            url: contextPath + '/products',
            method: 'GET',
            params: {
                min_price: $scope.filter ? $scope.filter.min_price : null,
                max_price: $scope.filter ? $scope.filter.max_price : null,
                name_part: $scope.filter ? $scope.filter.name_part : null,
                page: page ? page - 1 : null,
                size: $scope.filter ? $scope.filter.size : 5
            }
        }).then(function (response) {
            $scope.ProductsList = response.data.content;
            $scope.first = response.data.first;
            $scope.last = response.data.last;
            var pages = response.data.totalPages;
            $scope.PagesList = fillArray(pages);
        });
    };

    function fillArray(pages) {
        var pageArray = [];
        for (var i = 1; i <= pages; i++) {
            pageArray.push(i);
        }
        return pageArray;
    }

    $scope.openProduct = function (product){

    }

    $scope.deleteProductById = function (id){
        $http.delete(contextPath + '/products/' + id, null).then(function (){
            $scope.fillTable();
        });
    }

    $scope.submitCreateNewProduct = function () {
        $http.post(contextPath + '/products', $scope.newProduct)
            .then(function (response) {
                $scope.newProduct = null;
                $scope.fillTable();
            });
    };

    $scope.submitUpdateNewProduct = function () {
        $http.patch(contextPath + '/products', $scope.updatedProduct)
            .then(function (response) {
                $scope.newProduct = null;
                $scope.fillTable();
            });
    };

    $scope.numberPages = [
        { name: '5', value: 5 },
        { name: '10', value: 10 },
        { name: '20', value: 20}
    ];

    $scope.fillTable();
});
