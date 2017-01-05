var photoControllers = angular.module(
    'PhotoControllers', []);

photoControllers.controller('PhotoController', ['$scope', 'PhotoService', function ($scope, PhotoService) {
    $scope.attachments = [];
    $scope.attachments = PhotoService.getPhotos();

}]);