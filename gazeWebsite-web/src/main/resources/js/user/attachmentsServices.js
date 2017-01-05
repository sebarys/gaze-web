(function() {
    'use strict';

    angular.module('AttachmentsControllers')
        .factory('AttachmentsService', [
        '$resource', function ($resource) {
            return $resource('', {}, {

                getAttachment: {
                    params: {attachmentId: "@attachmentId"},
                    method: 'GET',
                    url: '/attachments/:attachmentId',
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
