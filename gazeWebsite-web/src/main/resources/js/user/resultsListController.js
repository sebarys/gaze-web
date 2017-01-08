(function() {
    'use strict';

    angular.module('ResultsControllers')
        .controller('ResultsListController',
        ['ResultService', '$http','$route', '$scope', '$routeParams', '$location', 'toaster',
        function (ResultService, $http, $route, $scope, $routeParams, $location, toaster) {
            var vm = this
            vm.pageSize = 5
            vm.totalPages = ''
            vm.currentPage = 0
            vm.items = {}
            vm.pager = {}
            vm.profileKeys = []
            vm.profileValues = []
            vm.selectedKey = ''
            vm.selectedValue = ''
            vm.filteredResults = ''
            vm.setPage = setPage
            vm.dateToString = dateToString
            vm.redirectToAddResults = redirectToAddResults
            vm.deleteResult = deleteResult
            vm.getResult = getResult
            vm.updateKeyValues = updateKeyValues
            vm.filterResults = filterResults
			vm.reset = reset

            var initialized = false
            var knownKeysValues = {
				"samochód" : ["tak", "nie"],
				"płeć" : ["mężczyzna", "kobieta"],
				"wiek" : ["0-20", "20-30", "30-40", "40-50", "50-60", "60+"],
				"wykształcenie" : ["niższe", "podstawowe", "średnie", "wyższe"],
				"kwalifikacje" : ["laik", "dentysta", "implantolog"]
			}

            function init() {
            	vm.filteredResults = false
                vm.pager = getPager(1, vm.pageSize)
            }
            init()

			function updateKeyValues() {
			    if(knownKeysValues[vm.selectedKey] != null){
					vm.profileValues = knownKeysValues[vm.selectedKey]
			    } else {
			       vm.profileValues = []
			    }
			}

			function filterResults() {
				vm.filteredResults =  ((vm.selectedKey != '') && (vm.selectedValue != ''))
				if(vm.filteredResults == true) {
					vm.pager = getPager(1, vm.pageSize);
				}
			}

			function reset() {
				vm.filteredResults = false
				vm.selectedKey = ''
				vm.selectedValue = ''
				vm.pager = getPager(1, vm.pageSize);
			}

			function redirectToAddResults() {
				if ($routeParams.stimulId === undefined || $routeParams.stimulId === null) {
					console.log("required route params undefined")
					return;
				}
				$location.path("/stimuls/" + $routeParams.stimulId + "/results/new")
			}

			function deleteResult(result) {
				if ($routeParams.stimulId === undefined || $routeParams.stimulId === null) {
					console.log("required route params undefined")
					return;
				}
				ResultService.deleteResult({stimulId: $routeParams.stimulId, resultId: result.id})
				.$promise.then( function (response) {
					console.log(response)
					if(response.success) {
						toaster.pop('success', "Success", response.info);
					} else {
						toaster.pop('error', "Error", response.info);
					}
					$route.reload();
				})
			}

			function getResult(result) {
				if ($routeParams.stimulId === undefined || $routeParams.stimulId === null) {
					console.log("required route params undefined")
					return;
				}
				console.log("try to get result")
				ResultService.getResult({stimulId: $routeParams.stimulId, resultId: result.id})
				.$promise.then( function (responseObj) {
					console.log(responseObj)
					var url = URL.createObjectURL(new Blob([responseObj.data]));
					var a = document.createElement('a');
					a.id = result.id;
					a.href = url;
					a.download = result.name;
					a.target = '_blank';
					a.click();
				})
				.catch(function(error) {
					console.log(error.data)
				})
			}

            function setPage(pageNumber) {
                if (pageNumber < 1 || pageNumber > vm.pager.totalPages) {
                    console.log("pageNumber out of scope")
					return;
                }
                if(initialized && vm.pager.currentPage == pageNumber) {
                	console.log("selected current page")
                	console.log("initialized: " + initialized + ", currPage: " + vm.pager.currentPage + ", pageNr: " + pageNumber)
                	return;
                }
                console.log("current page: " + vm.pager.currentPage + ", page nr: " + pageNumber)
                vm.pager = getPager(pageNumber, vm.pageSize);
            }

            function getPager(currentPage, pageSize) {
            	if ($routeParams.stimulId === undefined || $routeParams.stimulId === null) {
					console.log("required route params undefined")
					return;
				}

                // default to first page
                currentPage = currentPage || 1;
                pageSize = pageSize || 5;

				if(vm.filteredResults == true && vm.selectedValue != null) {
					ResultService.getFilteredResultPage({stimulId: $routeParams.stimulId,
					pageNumber: (currentPage - 1), pageSize: pageSize, key: vm.selectedKey, value: vm.selectedValue})
					.$promise.then(function (page) {
						createPage(page, currentPage, pageSize)
					})
				} else {
					ResultService.getResultPage({stimulId: $routeParams.stimulId, pageNumber: (currentPage - 1),
												pageSize: pageSize}).$promise.then(function (page) {
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

				if(initialized == false) {
					vm.profileKeys = []
					for(var k in vm.items[0].profile) {
						if(k != "PHPSESSID")
							vm.profileKeys.push(k);
					}
					initialized = true
				}

				console.log("keys: " + JSON.stringify(vm.profileKeys))

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
