app.controller('UsersController', ['$scope', '$sce', '$route', 'UserService', function ($scope, $sce, $route, userService) {

    userService.getAll($route.current.params.id).then(function (data) {
        $scope.data = data;
    })
}]);