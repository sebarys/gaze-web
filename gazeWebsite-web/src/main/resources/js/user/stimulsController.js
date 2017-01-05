(function() {
    'use strict';

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
            vm.registerStimul = registerStimul

            $scope.uploadedFile = function(element) {
                $scope.$apply(function($scope) {
                    vm.files = element.files;
                });
            }

            function registerStimul() {

                var fd = new FormData()
                fd.append('stimulName', vm.stimulName)
                fd.append('file', vm.files[0])

                $http.post('/stimuls', fd, {
                    transformRequest: angular.identity,
                    headers: {'Content-Type': undefined}
                })
                    .then(getStimulComplete)
                    .catch(getStimulFailed);
            }

            function getStimulComplete(data, status, headers, config) {
                console.log(data)
                vm.stimulCreated =  data;
            }

            function getStimulFailed(e) {
                var newMessage = 'XHR Failed for stimul registration'
                if (e.data && e.data.description) {
                    newMessage = newMessage + '\n' + e.data.description;
                }
                e.data.description = newMessage;
                logger.error(newMessage);
                return $q.reject(e);
            }
            
        }])

})();
