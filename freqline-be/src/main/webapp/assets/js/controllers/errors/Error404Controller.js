app.controller('Error404Controller', ['$scope', '$location', function ($scope, $location) {
    $scope.location = $location.$$path;
}]);