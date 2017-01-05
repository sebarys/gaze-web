(function() {
    'use strict';

    angular.module('ResultsControllers')
        .factory('ResultService', [
        '$resource', function ($resource) {
            return $resource('', {}, {

                getResults: {
                    method: 'GET',
                    url: '/results',
                    isArray: true
                },

                getResult: {
                    params: {resultId: "@resultId"},
                    method: 'GET',
                    url: '/results/:resultId',
                }
            });
        }]);

})();
