(function() {
    'use strict';

    angular.module('ResultsControllers')
        .controller('ResultsController',
        ['ResultService', '$http','$route', '$scope', '$routeParams', 'toaster',
         function (ResultService, $http, $route, $scope, $routeParams, toaster) {
            var vm = this
            vm.addResult = addResult

            $scope.uploadedFile = function(element) {
				$scope.$apply(function($scope) {
					vm.files = element.files;
				});
			}

			function addResult() {
				if ($routeParams.stimulId === undefined || $routeParams.stimulId === null) {
					console.log("required route params undefined")
					return;
				}
                if(vm.files == null) {
                	toaster.pop('error', "Upload file", "Please add result file to upload");
                	return
                }
				var fd = new FormData()
				fd.append('file', vm.files[0])

				$http.post('/stimuls/' + $routeParams.stimulId + '/results', fd, {
					transformRequest: angular.identity,
					headers: {'Content-Type': undefined}
				})
					.then(getResultComplete)
					.catch(getResultFailed);
			}

			function getResultComplete(data, status, headers, config) {
				console.log(data)
				vm.stimulCreated =  data;
				document.getElementById('result-file-upload').value = null;
				if(data.data.success) {
					toaster.pop('success', "Success", data.data.info);
				} else {
					toaster.pop('error', "Error", data.data.info);
				}
			}

			function getResultFailed(e) {
				var newMessage = 'XHR Failed for result registration'
				if (e.data && e.data.description) {
					newMessage = newMessage + '\n' + e.data.description;
				}
				e.data.description = newMessage;
				logger.error(newMessage);
				return $q.reject(e);
			}

        }]);

})();
