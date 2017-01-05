(function() {
    'use strict';
    angular.module('StimulsControllers', ['ngResource']);
    angular.module('ResultsControllers', ['ngResource']);
    angular.module('AttachmentsControllers', ['ngResource']);

    var mainApp = angular.module("mainApp", ['ngRoute', 'StimulsControllers', 'ResultsControllers', 'AttachmentsControllers', 'IndexServices']);

    mainApp.config(['$routeProvider', function ($routeProvider) {
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
                controller: 'ResultsController',
                controllerAs: 'results'
            }).

            when('/stimuls/:stimulId/results/add', {
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


