app.controller('Error404Controller', ['$scope', '$location', '$rootScope', function ($scope, $location, $rootScope) {
    if ($rootScope.error != null) {
        $scope.location = $rootScope.error.data.path;
        $rootScope.error = null;
    } else {
        $scope.location = $location.$$path;
    }
}]);