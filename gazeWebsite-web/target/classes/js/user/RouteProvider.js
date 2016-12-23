(function() {
    'use strict';

    var mainApp = angular.module("mainApp", ['ngRoute', 'StimulsControllers', 'ResultsControllers', 'IndexServices']);

    mainApp.config(['$routeProvider', function ($routeProvider) {
        $routeProvider.
            when('/stimuls', {
                templateUrl: '/templates/user/stimuls.html',
                controller: 'StimulsListController',
                controllerAs: 'stimulsList'
            }).

            when('/stimuls/:stimulId', {
                templateUrl: '/templates/user/stimulDetails.html',
                controller: 'StimulsController',
                controllerAs: 'stimuls'
            }).

            when('/results', {
                templateUrl: '/templates/user/results.html',
                controller: 'ResultsController',
                controllerAs: 'results'
            }).

            when('/results/:resultId', {
                templateUrl: '/templates/user/resultDetails.html',
                controller: 'ResultsController',
                controllerAs: 'results'
            }).

            when('/results/new', {
                templateUrl: '/templates/user/createResult.html',
                controller: 'ResultsController',
                controllerAs: 'results'
            }).

            otherwise({
                templateUrl: '/templates/user/welcome.html',
                controller: 'StimulsController',
                controllerAs: 'stimuls'
            });
    }]);

    mainApp.controller('MainPageController',['$scope', 'IndexService', function($scope, IndexService) {
        var vm = this
        vm.username = ''

        function init() {
            IndexService.getLoggedUsername(function (response) {
                console.log(response)
                vm.username = response.name
            })
        }
        init()

    }]);

})();


