app.controller('LoginController', ['$scope', '$location', 'LoginService', function ($scope, $location, loginService) {
    $scope.login = function(login) {
        loginService.login(login.username, login.password).then(function (data) {
            if (data.message === "ok") {
                $location.path('/users');
            } else {
                $scope.message = data.message;
            }
        })
    }
}]);