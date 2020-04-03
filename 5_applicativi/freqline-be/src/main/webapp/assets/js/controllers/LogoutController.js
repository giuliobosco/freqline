app.controller('LogoutController', ['$scope', '$location', 'LogoutService', function ($scope, $location, logoutService) {
    $scope.logout = function() {
        logoutService.logout().then(function () {
            $location.path('/login');
        });
    }
}]);