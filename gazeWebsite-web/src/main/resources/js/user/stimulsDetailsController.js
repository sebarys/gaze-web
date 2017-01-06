(function() {
    'use strict';

    angular.module('StimulsControllers')
        .controller('StimulsDetailsController',
        ['StimulService','AttachmentsService', '$http','$route', '$scope', '$routeParams', '$location', 'toaster',
        function (StimulService, AttachmentsService, $http, $route, $scope, $routeParams, $location, toaster) {
            var vm = this
            vm.stimul = {}
            vm.downloadStimul = downloadStimul
            vm.getStimulDetails = getStimulDetails
            vm.deleteStimul = deleteStimul
            vm.getAttachment = getAttachment

            function init() {
            console.log("param " + $routeParams.stimulId)
            	if ($routeParams.stimulId === undefined || $routeParams.stimulId === null) {
                    vm.getStimulDetails(1);
                } else {
                    vm.getStimulDetails($routeParams.stimulId);
                }
			}
			init()

			function getStimulDetails(stimulId) {
			console.log("calling stimul " + stimulId + " details")
				StimulService.getStimulDetails({stimulId: stimulId}, function(data) {
					data.attachments.map(function(attachment) {
						var name = attachment.name
						attachment.name = name.substring(name.indexOf("/") + 1);
					})
					console.log(JSON.stringify(data))
					vm.stimul = data
				})
			}

            function downloadStimul() {

				if ($routeParams.stimulId === undefined || $routeParams.stimulId === null) {
					console.log("required route params undefined")
					return;
				}

                StimulService.getStimul({stimulId: $routeParams.stimulId}).$promise.then( function (responseObj) {
                    console.log(responseObj)
                    console.log(vm.newStimul)

                    var url = URL.createObjectURL(new Blob([responseObj.data]));
                    var a = document.createElement('a');
                    a.href = url;
                    a.download = vm.stimul.name + '.zip';
                    a.target = '_blank';
                    a.click();
                })
            }

            function deleteStimul(stimulId) {
            	if ($routeParams.stimulId === undefined || $routeParams.stimulId === null) {
					console.log("required route params undefined")
					return;
				}
				StimulService.deleteStimul({stimulId: $routeParams.stimulId}).$promise.then( function (response) {
					console.log(response)
					$location.path("/stimuls")
					if(response.success) {
						toaster.pop('success', "Success", response.info);
					} else {
						toaster.pop('error', "Error", response.info);
					}
				})
			}

			function getAttachment(attachment) {
				console.log("try to get attachment")
                AttachmentsService.getAttachment({attachmentId: attachment.id}).$promise.then( function (responseObj) {
                    console.log(responseObj)
					var url = URL.createObjectURL(new Blob([responseObj.data]));
					var a = document.createElement('a');
					a.id = attachment.id;
					a.href = url;
					a.download = attachment.name;
					a.target = '_blank';
					a.click();
				})
				.catch(function(error) {
				  	console.log(error.data)
				})
			}

        }])

})();
