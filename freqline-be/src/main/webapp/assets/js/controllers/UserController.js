app.controller('UserController', ['$scope', '$route', '$sce', '$location', '$rootScope', 'UserService', function ($scope, $route, $sce, $location, $rootScope, userService) {
    
    $scope.htmlTrusted = function(html) {
        return $sce.trustAsHtml(html);
    }
    
    let id = $route.current.params.id;
    
    $scope.master = {};
    $scope.empty = {};
    $scope.saveMessage = 'SAVE';
    $scope.hideDelete = 'true';
    
    $scope.save = function(user) {//TODO fix password (1 new user, 2 update no pw, 3 update with pw)
        if (user.passwordNew == user.passwordCheck) {
            if (id == 0) {
                user.password = user.passwordNew;
                
                userService.insert(user).then(function(data) {
                    if (data.message != null) {
                        $scope.error = data.message;
                    } else if (data.missingParameters != null) {
                        let s = "Missing parameters: <ul>";
                        data.missingParameters.forEach(element => {
                            s += "<li>" + element + "</li>";
                        });
                        s += "</ul>";
                        $scope.error = s;
                    } else if (data.id != null) {
                        $location.path('/user/' + data.id);
                    }
                })
            } else {
                user.id = id;
                userService.update(user).then(function(data) {
                    if (data.message != null) {
                        $scope.error = data.message;
                    } else if (data.missingParameters != null) {
                        let s = "Missing parameters: <ul>";
                        data.missingParameters.forEach(element => {
                            s += "<li>" + element + "</li>";
                        });
                        s += "</ul>";
                        $scope.error = s;
                    }
                })
            }
        }
    };
    
    $scope.reset = function() {
        if (id == 0) {
            $scope.user = angular.copy($scope.empty);
            $scope.saveMessage = 'SAVE';
            $scope.hideDelete = 'true';
            
        } else {
            userService.getById(id).then(function(data) {
                $scope.user = angular.copy(data);
                $scope.saveMessage = 'UPDATE';
                $scope.hideDelete = 'false';
            })
        }
    };
    
    $scope.delete = function() {
        if (id != 0) {
            userService.delete(id).then(function(data) {
                $location.path('/users')
            })
        }
    }
    
    $scope.reset();
}]);