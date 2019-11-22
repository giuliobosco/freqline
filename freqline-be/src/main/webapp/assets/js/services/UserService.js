app.factory('UserService', ['$http', function($http) {
    var service = {};
	var address = "localhost";
	var port = 8080;
    var baseApi = "/freqline-be"
    var urlBase = "http://" + address + ":" + port + baseApi;
    urlBase += "/data/user";
    $http.defaults.withCredentials = true;

    service.getById = function (id) { 
        let url = urlBase + "/" + id;

        return $http({
            method: 'GET',
            url: url
        }).then(function (response){
            return response.data;
        },function (error){
            return error;
        });
    };

    service.getAll = function () { 
        return $http({
            method: 'GET',
            url: urlBase
        }).then(function (response){
            return response.data;
        },function (error){
            return error;
        });
    };

    service.delete = function (id) { 
        let url = urlBase + "/" + id;

        return $http({
            method: 'DELETE',
            url: url
        }).then(function (response){
            return response.data;
        },function (error){
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

        return $http({
            method: 'POST',
            url: url
        }).then(function (response){
            return response.data;
        },function (error){
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

        return $http({
            method: 'POST',
            url: url
        }).then(function (response){
            return response.data;
        },function (error){
            return error;
        });
    };

    return service;
}]);
