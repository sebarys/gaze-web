(function() {
    'use strict';

    angular.module('ResultsControllers')
        .factory('ResultService', [
        '$resource', function ($resource) {
            return $resource('', {}, {

                getResults: {
                	params: {stimulId: "@stimulId"},
                    method: 'GET',
                    url: '/stimuls/:stimulId/results',
                    isArray: true
                },

                getResultPage: {
					params: {stimulId: "@stimulId", pageNumber: "@pageNumber", pageSize: "@pageSize"},
					method: 'GET',
					url: '/stimuls/:stimulId/results?page=:pageNumber&size=:pageSize'
				},

				getFilteredResultPage: {
					params: {stimulId: "@stimulId", pageNumber: "@pageNumber", pageSize: "@pageSize",
							key: "@key", value: "@value"},
					method: 'GET',
					url: '/stimuls/:stimulId/results?page=:pageNumber&size=:pageSize&key=:key&value=:value'
				},

                getResult: {
                    params: {stimulId: "@stimulId", resultId: "@resultId"},
                    method: 'GET',
                    url: '/stimuls/:stimulId/results/:resultId',
					responseType: 'arraybuffer',
					transformResponse: function(data) {
						return {
							data: data
						}
					}
                },

                deleteResult: {
					params: {stimulId: "@stimulId", resultId: "@resultId"},
					method: 'DELETE',
					url: '/stimuls/:stimulId/results/:resultId'
				},

                registerResult: {
                	params: {stimulId: "@stimulId"},
					method: 'POST',
					url: '/stimuls/:stimulId/results'
				}
            });
        }]);

})();
