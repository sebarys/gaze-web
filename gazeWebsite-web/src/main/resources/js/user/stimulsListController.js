(function() {
    'use strict';

    angular.module('StimulsControllers')
        .controller('StimulsListController',
        ['StimulService', '$http','$route', '$scope', '$routeParams', function (StimulService, $http, $route, $scope, $routeParams) {
            var vm = this
            vm.pageSize = 5
            vm.totalPages = ''
            vm.currentPage = 0
            vm.items = {}
            vm.pager = {}
            vm.searchPhrase = ''
            vm.searchingByName = ''
            vm.setPage = setPage
            vm.dateToString = dateToString
            vm.searchByName = searchByName
            vm.reset = init

            function init() {
            	vm.searchingByName = false
            	vm.searchPhrase = ''
                vm.pager = getPager(1, vm.pageSize);
            }
            init()

            function searchByName() {
            	if(vm.searchPhrase == '') {
            		return
            	}
            	vm.searchingByName = true
            	vm.pager = getPager(1, vm.pageSize);
            }

            function setPage(pageNumber) {
                if (pageNumber < 1 || pageNumber > vm.pager.totalPages) {
                    return;
                }
                if( vm.pager.currentPage == pageNumber) {
					console.log("selected current page")
					console.log("currPage: " + vm.pager.currentPage + ", pageNr: " + pageNumber)
					return;
				}
                vm.pager = getPager(pageNumber, vm.pageSize);
            }

            function getPager(currentPage, pageSize) {

                // default to first page
                currentPage = currentPage || 1;
                pageSize = pageSize || 5;

				if(vm.searchingByName) {
					StimulService.getStimulByName(
					{pageNumber: (currentPage - 1), pageSize: pageSize, searchPhrase: vm.searchPhrase}).$promise.then(function (page) {
					 	createPage(page, currentPage, pageSize)
					})
				} else {
					StimulService.getStimulPage({pageNumber: (currentPage - 1), pageSize: pageSize}).$promise.then(function (page) {
                    	createPage(page, currentPage, pageSize)
                    })
				}
            }

            function createPage(page, currentPage, pageSize) {
				console.log(JSON.stringify(page))
				var totalPages = page.totalPages || 1
				vm.totalElements = page.totalElements
				vm.items = page.content
				vm.items.forEach(function (stimul) {
					stimul.created = dateToString(stimul.created)
				})

				var startPage, endPage;
				if (totalPages <= 10) {
					// less than 10 total pages so show all
					startPage = 1;
					endPage = totalPages - 1;
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
					pages: pages
				};
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
