angular.module("directives", [])
.directive('siteHeader', function () {
    return {
        restrict: 'E',
        template: '<button style:"padding-left: 15px;">{{Back}}</button>',
        scope: {
            Back: '@back',
            icons: '@icons'
        },
        link: function(scope, element, attrs) {
            $(element[0]).on('click', function() {
                history.back();
                scope.$apply();
            });
        }
    };
});