(function() {
    'use strict';

    angular.module('StimulsControllers')
        .factory('StimulService', [
        '$resource', function ($resource) {
            return $resource('', {}, {

                getStimuls: {
                    method: 'GET',
                    url: '/stimuls',
                    isArray: true
                },

                getStimul: {
                    params: {stimulId: "@stimulId"},
                    method: 'GET',
                    url: '/stimuls/:stimulId'
                },

                getStimulPage: {
                    params: {pageNumber: "@pageNumber", pageSize: "@pageSize"},
                    method: 'GET',
                    url: '/stimuls?page=:pageNumber&size=:pageSize'
                },

                deleteStimul: {
                    params: {stimulId: "@stimulId"},
                    method: 'DELETE',
                    url: '/stimuls/:stimulId'
                },

                registerStimul: {
                    method: 'POST',
                    url: '/stimuls'
                },

                testGet: {
                    params: {stimulId: "@stimulId"},
                    method: 'GET',
                    url: '/stimuls/:stimulId',
                    responseType: 'arraybuffer',
                    transformResponse: function(data) {
                        return {
                            data: data
                        }
                    }

                }
            });
        }]);

})();