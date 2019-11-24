app.factory('UserGroupService', ['$http', function($http) {
    var config = {headers: [{'Access-Control-Allow-Origin': ' *' },{'Access-Control-Allow-Credentials':'true'}]};
    var service = {};
	var address = "localhost";
    var port = 8080;
    var baseApi = "/freqline-be"
    var urlBase = "http://" + address + ":" + port + baseApi;
	urlBase += "/data/userGroup";
    $http.defaults.withCredentials = true;

    service.getAll = function() {
        let url = urlBase + "?t=" + new Date().getTime();

        let data = {};

        return $http.get(url, data, config).then(function (response) {
            return response.data;
        }, function(error) {
            if (error.status == 401) {
                $rootScope.error = error;
                $location.path('/401');
                return error;
            } else {
                return error;
            }
        })
    };
    
    service.getById = function (id) { 
        let url = urlBase + "/" + id;
        let data = 't=' + new Date().getTime();
        
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

    service.insert = function(user, group) {
        let url = urlBase + "?user=" + user + "&group=" + group;
        let data = "t=" + new Date().getTime();

        return $http.post(url, data, config).then(function (response) {
            return response;
        }, function (error) {
            return error;
        })
    }

    service.update = function(id, user, group) {
        let url = urlBase + "?id=" + id + "&user=" + user + "&group=" + group;
        let data = "t=" + new Date().getTime();
        
        return $http.post(url, data, config).then(function (response) {
            return response;
        }, function (error) {
            return error;
        })
    }

    return service;
}]);
