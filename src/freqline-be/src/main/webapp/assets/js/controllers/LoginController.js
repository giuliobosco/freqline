app.controller('LoginController', ['$scope', '$location', 'LoginService', function ($scope, $location, loginService) {
    $scope.login = function(loginData) {
        loginService.login(loginData.username, loginData.password).then(function (data) {
            console.log(data)
            if (data.status === 200) {
                $location.path('/users');
            } else {
                $scope.message = data.data.message;
            }
        });
    }
}]);