var PhotoServices = angular.module(
    "PhotoServices", ['ngResource']);

PhotoServices.factory('PhotoService', [
    '$resource', function ($resource) {
        return $resource('', {}, {
            getPhoto: {
                params: {id: "@id"},
                method: 'GET',
                url: '../photo/:id'
            },
            postGret: {
                params: {id: "@id"},
                method: 'POST',
                url: '../gret/save/:id'
            },

            getPhotos: {
                method: 'GET',
                url: '../attachment/all',
                isArray:true
            },

            postUser: {
                method: 'POST',
                url: '../user'
                //               isArray: true
            }
        });
    }]);