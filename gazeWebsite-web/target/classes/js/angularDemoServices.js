var angularDemoServices = angular.module(
    "AngularDemoServices", ['ngResource']);

angularDemoServices.factory('AngularDemoService', [
    '$resource', function ($resource) {
        return $resource('', {}, {
            getSection: {
                params: {id: "@id"},
                method: 'GET',
                url: '../section/:id'
            },
            deleteSection: {
                method: 'POST',
                url: '../section/delete/:id'
            },
            logout: {
                method: 'GET',
                url: '/logout'
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