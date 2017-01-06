var loginControllers = angular.module(
    'loginControllers', ['ngResource']);

loginControllers.controller('loginController',
    ['$scope','$location', 'LoginService',
        function($scope,$location, LoginService){

    $scope.user = {}
    $scope.user.username = ''
    $scope.user.password = ''
    $scope.nextUrl = function(){
        return $location.url();
    }

    $scope.login = function () {
        console.log($scope.user)
        LoginService.login($scope.user, function () {
            console.log("Send post with user login")
        })
    }
}]);

loginControllers.factory('LoginService', [
    '$resource', function ($resource) {
        return $resource('', {}, {
            login: {
                method: 'POST',
                url: '../login?p=2'
            }
        })
    }
]);

