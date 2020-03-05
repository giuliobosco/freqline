app.factory('UserService', ['$http', '$location', 'baseUrl', '$rootScope', function($http, $location, baseUrl, $rootScope) {
    let config = {headers:[]};// [{'Access-Control-Allow-Origin': ' *' },{'Access-Control-Allow-Credentials':'true'}]};//, {'Content-Type': 'application/json'}]};
    var service = {};
    var serviceUrl = baseUrl + "/data/user";
    $http.defaults.withCredentials = true;
    
    service.getById = function (id) { 
        let url = serviceUrl + "/" + id;
        let data = '';
        
        return $http.get(url, data, config).then(function (response){
            return response.data;
        },function (error){
            if (error.status == 401) {
                $rootScope.error = error;
                $location.path('/401');
                return error;
            } else if (error.status == 404) {
                $rootScope.error = error;
                $location.path('/404');
            } else {
                return error.data;
            }
        });
    };
    
    service.getAll = function () { 
        let data = '';
        return $http.get(serviceUrl, data, config).then(function (response){
            return response.data;
        },function (error){
            if (error.status == 401) {
                $rootScope.error = error;
                $location.path('/401');
                return error;
            } else {
                return error;
            }
        });
    };
    
    service.delete = function (id) { 
        let url = serviceUrl + "/" + id;
        let data = '';
        return $http.delete(url, data, config).then(function (response){
            $.notify("Deleted correctly", "success");
            return response.data;
        },function (error){
            if (error.status == 401) {
                $.notify("Operation not permitted", "error")
                return error;
            } else {
                return error;
            }
        });
    };
    
    service.insert = function (user) { 
        let url = serviceUrl + "?";
        
        if (user.username != null && user.username.length > 0) {
            url += "username=" + user.username + "&";
        }
        if (user.password != null && user.password.length > 0) {
            url += "password=" + user.password + "&";
        }
        url += "salt=salt&";
        if (user.firstname != null && user.firstname.length > 0) {
            url += "firstname=" + user.firstname + "&";
        }
        if (user.lastname != null && user.lastname.length > 0) {
            url += "lastname=" + user.lastname + "&";
        }
        if (user.email != null && user.email.length > 0) {
            url += "email=" + user.email + "&";
        } 
        url += "favoriteGenerator=" + user.favoriteGenerator;
        
        let data = '';
        return $http.post(url, data, config).then(function (response) {
            $.notify("Created correctly", "success");
            return response.data;
        },function (error){
            if (error.status == 401) {
                $.notify("Operation not permitted", "error")
                return error;
            } else {
                return error.data;
            }
        });
    };
    
    service.update = function (user) { 
        let url = serviceUrl + "?";
        
        url += "id=" + user.id + "&";
        if (user.username != null && user.username.length > 0) {
            url += "username=" + user.username + "&";
        }
        if (user.password != null && user.password.length > 0) {
            url += "password=" + user.password + "&";
        }
        url += "salt=salt&";
        if (user.firstname != null && user.firstname.length > 0) {
            url += "firstname=" + user.firstname + "&";
        }
        if (user.lastname != null && user.lastname.length > 0) {
            url += "lastname=" + user.lastname + "&";
        }
        if (user.email != null && user.email.length > 0) {
            url += "email=" + user.email + "&";
        } 
        url += "favoriteGenerator=" + user.favoriteGenerator;

        let data = '';
        
        return $http.put(url, data, config).then(function (response){
            $.notify("Uploaded correctly", "success");
            return response.data;
        },function (error){
            if (error.status == 401) {
                $.notify("Operation not permitted", "error")
                return error;
            } else {
                return error.data;
            }
        });
    };
    
    return service;
}]);
