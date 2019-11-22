app.controller('UsersController', ['$scope', '$location', 'UserService', function ($scope, $location, userService) {
    
    userService.getAll().then(function(data) {
        $scope.data = data;
    });
}]);