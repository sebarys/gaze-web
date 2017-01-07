(function() {
    'use strict';
    angular.module('StimulsControllers', ['toaster', 'ngAnimate', 'ngResource']);
    angular.module('ResultsControllers', ['toaster', 'ngAnimate', 'ngResource']);
    angular.module('AttachmentsControllers', ['ngResource']);

    var mainApp = angular.module("mainApp", ['ngRoute', 'StimulsControllers', 'ResultsControllers', 'AttachmentsControllers', 'IndexServices', 'directives']);

    mainApp.config(['$routeProvider', '$locationProvider', function ($routeProvider, $locationProvider) {
        $routeProvider.
            when('/stimuls', {
                templateUrl: '/templates/user/stimuls.html',
                controller: 'StimulsListController',
                controllerAs: 'stimulsList'
            }).

             when('/stimuls/new', {
				templateUrl: '/templates/user/addStimul.html',
				controller: 'StimulsController',
				controllerAs: 'stimuls'
			}).

            when('/stimuls/:stimulId', {
                templateUrl: '/templates/user/stimulsDetails.html',
                controller: 'StimulsDetailsController',
                controllerAs: 'stimulDetails'
            }).

            when('/stimuls/:stimulId/results', {
                templateUrl: '/templates/user/results.html',
                controller: 'ResultsListController',
                controllerAs: 'results'
            }).

            when('/stimuls/:stimulId/results/new', {
                templateUrl: '/templates/user/addResult.html',
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

	   	$locationProvider.hashPrefix('');
    }]);

    mainApp.controller('MainPageController',['$scope', 'IndexService', function($scope, IndexService) {
        var vm = this
        vm.userInfo = {}

        function init() {
            IndexService.getLoggedUsername(function (response) {
                console.log(response)
                vm.userInfo = response
            })
        }
        init()

    }]);

})();


