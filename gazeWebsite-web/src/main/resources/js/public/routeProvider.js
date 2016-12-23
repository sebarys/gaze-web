var mainApp = angular.module("mainApp", ['ngRoute', 'ngAnimate','toaster','IndexControllers', 'IndexServices'
    , 'AngularDemoControllers', 'AngularDemoServices'
    , 'SearchServices', 'SearchControllers', 'ui.grid', 'ui.grid.pagination', 'ErrorControllers'
    , 'OfferDetailsServices', 'OfferDetailsControllers']);
mainApp.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.

        when('/angularDemo/:param', {
            templateUrl: '/templates/angularDemo.html',
            controller: 'AngularDemoController'
        }).

        when('/offer/:offerId', {
            templateUrl: '/templates/public/offerDetails.html',
            controller: 'OfferDetailsController'
        }).

        when('/error/:reasonOfError', {
            templateUrl: '/templates/public/error.html',
            controller: 'ErrorController'
        }).

        when('/search', {
            templateUrl: '/templates/public/searchPage.html',
            controller: 'SearchController'
        }).

        when('/search/:pageNumber', {
            templateUrl: '/templates/public/searchPage.html',
            controller: 'SearchController'
        }).


        otherwise({
            templateUrl: '/templates/public/main.html',
            controller: 'MainPageController'
        });
}]);

mainApp.run(['$rootScope', '$location', 'IndexService', function ($rootScope, $location, IndexService) {
    $rootScope.$on('$routeChangeStart', function (event) {

            IndexService.getLoggedUsername(function (response) {
                if (username.value !== null) {
                    window.location = "/user/#" + $location.path();
                }
            });
            //window.alert($location.path().toString());
            //if (!Auth.isLoggedIn()) {
            //    console.log('DENY');
            //    event.preventDefault();
            //    $location.path('/login');
            //}
            //else {
            //    console.log('ALLOW');
            //    $location.path('/home');
            //}
        }
    );
}]);



