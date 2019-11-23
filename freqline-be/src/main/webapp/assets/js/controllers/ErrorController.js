app.controller('ErrorController', ['$scope', '$location', function ($scope, $location) {
    $scope.location = $location.$$path;
}]);