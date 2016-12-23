var mainApp = angular.module("mainApp", ['ngRoute', 'StimulsControllers', 'ResultsControllers']);
mainApp.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.

    when('/stimuls', {
        templateUrl: '/templates/user/stimuls.html',
        controller: 'StimulsController'
    }).

    when('/stimuls/new', {
        templateUrl: '/templates/user/createStimuls.html',
        controller: 'StimulsController'
    }).

    when('/stimuls/:stimulId', {
        templateUrl: '/templates/user/stimulDetails.html',
        controller: 'StimulsController'
    }).

    when('/results', {
        templateUrl: '/templates/user/results.html',
        controller: 'ResultsController'
    }).

    when('/results/:resultId', {
        templateUrl: '/templates/user/resultDetails.html',
        controller: 'ResultsController'
    }).

    when('/results/new', {
        templateUrl: '/templates/user/createResult.html',
        controller: 'ResultsController'
    }).

    otherwise({
        templateUrl: '/templates/admin/adminIndex.html',
        controller: 'MainPageController'
    });


        otherwise({
            templateUrl: '/templates/public/main.html',
            controller: 'MainPageController'
        });
}]);

mainApp.run(['$rootScope', '$location', 'IndexService', function ($rootScope, $location, IndexService) {
    $rootScope.$on('$routeChangeStart', function (event) {

            IndexService.getLoggedUsername(function (username) {
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



