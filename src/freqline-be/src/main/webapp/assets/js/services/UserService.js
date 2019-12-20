app.factory('UserService', ['$http', '$location', '$rootScope', function($http, $location, $rootScope) {
    let config = {headers: [{'Access-Control-Allow-Origin': ' *' },{'Access-Control-Allow-Credentials':'true'}, {'Content-Type': 'application/json'}]};
    var service = {};
    var address = "localhost";
    var port = 8080;
    var baseApi = "/freqline-be"
    var urlBase = "http://" + address + ":" + port + baseApi;
    urlBase += "/data/user";
    $http.defaults.withCredentials = true;
    
    service.getById = function (id) { 
        let url = urlBase + "/" + id;
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
        return $http.get(urlBase, data, config).then(function (response){
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
        let url = urlBase + "/" + id;
        let data = '';
        return $http.delete(url, data, config).then(function (response){
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
    
    service.insert = function (user) { 
        let url = urlBase + "?";
        
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
            return response.data;
        },function (error){
            if (error.status == 401) {
                $rootScope.error = error;
                $location.path('/401');
                return error;
            } else {
                return error.data;
            }
        });
    };
    
    service.update = function (user) { 
        let url = urlBase + "?";
        
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
            return response.data;
        },function (error){
            if (error.status == 401) {
                $rootScope.error = error;
                $location.path('/401');
                return error;
            } else {
                return error.data;
            }
        });
    };
    
    return service;
}]);
