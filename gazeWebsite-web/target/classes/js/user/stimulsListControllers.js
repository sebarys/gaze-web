(function() {
    'use strict';

    angular.module('StimulsControllers', ['ngResource']);

    angular.module('StimulsControllers')
        .controller('StimulsListController',
        ['StimulService', '$http','$route', '$scope', '$routeParams', function (StimulService, $http, $route, $scope, $routeParams) {
            var vm = this
            vm.pageSize = 5
            vm.totalPages = ''
            vm.currentPage = 0
            vm.items = {}
            vm.pager = {}
            vm.setPage = setPage
            vm.dateToString = dateToString

            function init() {
                vm.setPage(1);
            }
            init()

            function setPage(pageNumber) {
                if (pageNumber < 1 || pageNumber > vm.pager.totalPages) {
                    return;
                }
                vm.pager = getPager(pageNumber, vm.pageSize);
            }

            // service implementation
            function getPager(currentPage, pageSize) {

                // default to first page
                currentPage = currentPage || 1;
                // default page size is 10
                pageSize = pageSize || 10;

                StimulService.getStimulPage({pageNumber: currentPage-1, pageSize: pageSize}).$promise.then(function (page) {
                    var totalPages = page.totalPages
                    vm.totalElements = page.totalElements
                    vm.items = page.content
                    vm.items.forEach(function (stimul) {
                        stimul.created = dateToString(stimul.created)
                    })

                    var startPage, endPage;
                    if (totalPages <= 10) {
                        // less than 10 total pages so show all
                        startPage = 1;
                        endPage = totalPages;
                    } else {
                        // more than 10 total pages so calculate start and end pages
                        if (currentPage <= 6) {
                            startPage = 1;
                            endPage = 10;
                        } else if (currentPage + 4 >= totalPages) {
                            startPage = totalPages - 9;
                            endPage = totalPages;
                        } else {
                            startPage = currentPage - 5;
                            endPage = currentPage + 4;
                        }
                    }

                    // create an array of pages to ng-repeat in the pager control
                    var pages = range(startPage, endPage + 1);

                    // return object with all pager properties required by the view
                    vm.pager = {
                        totalItems: vm.totalElements,
                        currentPage: currentPage,
                        pageSize: pageSize,
                        totalPages: totalPages,
                        startPage: startPage,
                        endPage: endPage,
                        // startIndex: startIndex,
                        // endIndex: endIndex,
                        pages: pages
                    };
                })
            }

            function range(start, count) {
                return Array.apply(0, Array(count))
                    .map(function (element, index) {
                        return index + start;
                    });
            }

            function dateToString(dateAsLong) {
                var date = new Date(dateAsLong);
                return date.toString()
            }
            
        }])

})();
