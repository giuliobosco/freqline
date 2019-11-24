app.controller('UserController', ['$scope', '$route', '$sce', '$location', 'GroupService',  'UserGroupService', 'UserService', function ($scope, $route, $sce, $location, groupService, userGroupService, userService) {
    
    $scope.htmlTrusted = function(html) {
        return $sce.trustAsHtml(html);
    }
    
    let id = $route.current.params.id;
    let userGroupId = null;
    
    $scope.master = {};
    $scope.empty = {};
    $scope.saveMessage = 'SAVE';
    $scope.hideDelete = 'true';
    $scope.hidePasswordCheck = 'false';
    
    $scope.save = function(user) {//TODO fix password (1 new user, 2 update no pw, 3 update with pw)
        if (id == 0) {
            if (user.passwordNew == user.passwordCheck) {
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
                        userGroupService.insert(data.id, user.group);
                        $location.path('/user/' + data.id);
                    } else {
                        $scope.error = "Error";
                    }
                })
            } else {
                $scope.error = "Password do not match";
            }
        } else {
            user.id = id;
            if (user.passwordNew != '********') {
                if (user.passwordNew == user.passwordCheck) {
                    user.password = user.passwordNew;
                } else {
                    $scope.error = "Password do not match";
                    return '';
                }
            }
            
            userService.update(user).then(function(data) {
                userGroupService.update(userGroupId, id, user.group);
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
    };
    
    $scope.reset = function() {
        if (id == 0) {
            $scope.user = angular.copy($scope.empty);
            $scope.saveMessage = 'SAVE';
            $scope.hideDelete = 'true';
            $scope.hidePasswordCheck = 'false';
            
        } else {
            userService.getById(id).then(function(data) {
                $scope.user = angular.copy(data);
                $scope.saveMessage = 'UPDATE';
                $scope.hideDelete = 'false';
                $scope.hidePasswordCheck = 'true';
                $scope.user.passwordNew = '********';
                $scope.user.passwordCheck = '********';
    
                groupService.getByUserId(id).then(function(data) {
                    if (data != null) {
                        $scope.group = data.group;
                    }
                });
            
                groupService.getAll().then(function(data) {
                    $scope.groups = data.data;
                });
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

    $scope.showPasswordCheck = function() {
        $scope.hidePasswordCheck = 'false';
    }
    
    $scope.reset();
}]);