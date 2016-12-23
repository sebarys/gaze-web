(function() {
    'use strict';

    angular.module('StimulsControllers', ['ngResource']);

    angular.module('StimulsControllers')
        .controller('StimulsController',
        ['StimulService', '$http','$route', '$scope', '$routeParams', function (StimulService, $http, $route, $scope, $routeParams) {
            var vm = this
            vm.newStimul = {}
            vm.files = {}
            vm.stimulName = ''
            vm.stimulId = ''
            vm.stimulToDeleteId = ''
            vm.pageSize = 5
            vm.totalPages = ''
            vm.currentPage = 0
            vm.getStimul = getStimul
            vm.registerStimul = registerStimul
            vm.deleteStimul = deleteStimul
            vm.getPage = getPage


            getPage(vm.currentPage, vm.pageSize)

            $scope.uploadedFile = function(element) {
                $scope.$apply(function($scope) {
                    vm.files = element.files;
                });
            }
            
            function getStimul() {
                StimulService.testGet({stimulId: vm.stimulId}).$promise.then( function (responseObj) {
                    console.log(responseObj)
                    console.log(vm.newStimul)

                    var url = URL.createObjectURL(new Blob([responseObj.data]));
                    var a = document.createElement('a');
                    a.href = url;
                    a.download = 'document_name.zip';
                    a.target = '_blank';
                    a.click();
                })
            }

            function registerStimul() {

                var fd = new FormData()
                fd.append('stimulName', vm.stimulName)
                fd.append('file', vm.files[0])

                $http.post('/stimuls', fd, {
                    transformRequest: angular.identity,
                    headers: {'Content-Type': undefined}
                })
                    .then(getCustomerComplete)
                    .catch(getCustomerFailed);
            }

            function getCustomerComplete(data, status, headers, config) {
                console.log(data)
                vm.stimulCreated =  data;
            }

            function getCustomerFailed(e) {
                var newMessage = 'XHR Failed for getCustomer'
                if (e.data && e.data.description) {
                    newMessage = newMessage + '\n' + e.data.description;
                }
                e.data.description = newMessage;
                logger.error(newMessage);
                return $q.reject(e);
            }

            function deleteStimul(stimulId) {
                StimulService.deleteStimul({stimulId: stimulId}).$promise.then( function (response) {
                    console.log(response)
                })
            }
            
            function getPage(pageNumber, pageSize) {
                StimulService.getStimulPage({pageNumber: pageNumber, pageSize: pageSize}).$promise.then(function (page) {
                    console.log('inside pagefunc: ' + JSON.stringify(page))
                })
            }
            
        }])

})();
