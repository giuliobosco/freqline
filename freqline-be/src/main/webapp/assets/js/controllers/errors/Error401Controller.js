app.controller('Error401Controller', ['$scope', '$rootScope', 'LoginService', function ($scope, $rootScope, loginService) {
    loginService.isLoggedIn().then(function(data) {
        $scope.isLoggedIn = data.response;
    });
    
    let d = $rootScope.error.data;
    $scope.path = d.path;
    $scope.string = d.statusString;

    if (d.method == "GET") {
        d = "require";
    } else if (d.method == "POST") {
        d = "create";
    } else if (d.method == "PUT") {
        d = "update";
    } else if (d.method == "DELETE") {
        d = "delete";
    }
    $scope.method = d;
}]);
