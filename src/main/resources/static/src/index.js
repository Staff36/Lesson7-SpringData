angular.module('app', []).controller('indexController', function ($scope, $http) {

    const contextPath = 'http://localhost:8080/api/v1';
    $scope.showCart = false;
    $scope.showSignUp = false;
    $scope.showSignIn = false;
    $scope.loggedIn = false;

    $scope.showSignInFunc = function (){
        if ($scope.showSignIn == false){
            $scope.showSignIn = true;
            $scope.showSignUp = false;
        } else {
            $scope.showSignIn = false;
        }
    };
    $scope.showSignUpFunc = function (){
        if ($scope.showSignUp == false){
            $scope.showSignUp = true;
            $scope.showSignIn = false;
        } else {
            $scope.showSignUp = false;
        }
    };

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

    $scope.changeShowCart = function () {
        $scope.showCart = !$scope.showCart;
    };

    $scope.fillOrderedProducts = function () {
        $http({
            url: contextPath + '/order',
            method: 'GET',
        }).then(function (response) {
            $scope.OrderedProducts = response.data;
        });
    };

    function fillArray(pages) {
        var pageArray = [];
        for (var i = 1; i <= pages; i++) {
            pageArray.push(i);
        }
        return pageArray;
    }

    $scope.signUp = function () {
        $http.post(contextPath + '/auth/signup', $scope.registrateBody)
            .then(function (response) {
            });
    };

    $scope.signIn = function () {
        $http.post(contextPath + '/auth/login', $scope.auth)
            .then(function (response) {
                $scope.token = 'Bearer ' + response.data.token;
                $http.defaults.headers.common['Authorization'] = $scope.token;
                $scope.loggedIn = true;
                $scope.showSignUp = false;
                $scope.showSignIn = false;
            });
    };

    $scope.logout = function (){
        $http.defaults.headers.common['Authorization'] = null;
        $scope.loggedIn = false;
    };


    $scope.removeOrderedProductFromCartById = function (id) {
        $http.delete(contextPath + '/order/' + id, null)
            .then(function () {
                $scope.fillOrderedProducts();
            });
    }

    $scope.deleteProductById = function (id) {
        $http.delete(contextPath + '/products/' + id, null).then(function () {
            $scope.fillTable();
        });
    }

    $scope.orderProduct = function (product, id) {

        $http.post(contextPath + '/order', product)
            .then(function (response) {
                $scope.fillOrderedProducts();
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
        {name: '5', value: 5},
        {name: '10', value: 10},
        {name: '20', value: 20}
    ];
    $scope.fillOrderedProducts();
    $scope.fillTable();
});

