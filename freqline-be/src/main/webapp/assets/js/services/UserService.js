app.factory('UserService', ['$http', '$location', function($http, $location) {
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
            $location.path('/login');
            return error;
        });
    };
    
    service.getAll = function () { 
        let data = '';
        return $http.get(urlBase, data, config).then(function (response){
            return response.data;
        },function (error){
            $location.path('/login');
            return error;
        });
    };
    
    service.delete = function (id) { 
        let url = urlBase + "/" + id;
        let data = '';
        return $http.delete(url, data, config).then(function (response){
                return response.data;
        },function (error){
            $location.path('/login');
            return error;
        });
    };
    
    service.insert = function (user) { 
        let url = urlBase + "?username=" + user.username;
        url += "&password=" + user.password;
        url += "&salt=salt";
        url += "&firstname=" + user.firstname;
        url += "&lastname=" + user.lastname;
        url += "&email=" + user.email;
        url += "&favoriteGenerator=" + user.favoriteGenerator;
        
        let data = '';
        return $http.post(url, data, config).then(function (response){
            return response.data;
        },function (error){
            $location.path('/login');
            return error;
        });
    };
    
    service.update = function (user) { 
        let url = urlBase + "?username=" + user.username;
        if (user.username !== null) {
            url += "&password=" + user.password;
        }
        url += "&salt=salt";
        url += "&firstname=" + user.firstname;
        url += "&lastname=" + user.lastname;
        url += "&email=" + user.email;
        url += "&favoriteGenerator=" + user.favoriteGenerator;
        
        let data = '';
        
        return $http.put(url, data, config).then(function (response){
            return response.data;
        },function (error){
            $location.path('/login');
            return error;
        });
    };
    
    return service;
}]);
