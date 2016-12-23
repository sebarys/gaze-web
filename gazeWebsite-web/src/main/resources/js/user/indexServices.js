(function() {
    'use strict';

    var IndexServices = angular.module(
        "IndexServices", ['ngResource']);

    IndexServices.factory('IndexService', [
        '$resource', function ($resource) {
            return $resource('', {}, {

                getLoggedUsername: {
                    method: 'GET',
                    url: '/username'
                }
            });
        }]);

})();