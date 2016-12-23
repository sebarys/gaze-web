var indexControllers = angular.module(
    'IndexControllers', []);

indexControllers.controller('MainPageController', ['$scope', 'IndexService', '$location', function ($scope, IndexService, $location) {
    $scope.message = "Main page";

    $scope.searchPhrase = function (phrase) {

        if (typeof phrase === 'undefined') {
            $location.path('/search/1');
            //window.location = "../#/search/1";
        } else {
            //window.location = "/search/1?query=" + phrase;
            $location.search().query = phrase;
            $location.path('/search/1');
        }

    };

}]);

indexControllers.controller('LoginController', ['$scope', '$location', 'IndexService', function ($scope, $location, IndexService) {
    $scope.message = "Login page";

    $scope.nextUrl = function(){
        return $location.url();
    }

    $scope.username ='';
    IndexService.getLoggedUsername(function (username) {
        if(username!==null)
            $scope.username = username.value;
        });

    $scope.login = function () {
        IndexService.login($scope.user, function (data) {

            window.alert('succes');
        });

    }

    $scope.logout = function () {
        IndexService.logout(function () {
            IndexService.getLoggedUsername(function (username) {
                if (username.value === null) {
                    window.location = "/#" + $location.path();
                }
            });
        });
    }

}]);
