/**
 * Created by Mateusz on 2016-04-28.
 */

var offerDetailsServices = angular.module(
    'OfferDetailsServices', ['ngResource']);

offerDetailsServices.factory('OfferDetailsService', [
    '$resource', function ($resource) {
        return $resource('', {}, {
            getOfferDetails: {
                method: 'GET',
                url: '/offer/:offerId',
                params: {offerId: "@offerId"}
            },

            getAttachements: {
                method: 'GET',
                url: '/attachment/offer/:offerId',
                params: {offerId: "@offerId"},
                isArray: true
            },

            getUsername: {
                method: 'GET',
                url: '/account/logged/name'
            },

            addOrder: {
                method: 'POST',
                url: '/order/addToBasket'
            },

            getComments: {
                method: 'GET',
                url: '../comment/offer/:offerId',
                params: {offerId: "@OfferId"},
                isArray: true
            }
            ,
            addComment:{
                method: 'POST',
                url: '../comment/add'
            }
        });
    }]);
