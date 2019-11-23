app.factory('LoginService', ['$http', function($http) {
    var config = {headers: [{'Access-Control-Allow-Origin': ' *' },{'Access-Control-Allow-Credentials':'true'}]};
    var service = {};
	var address = "localhost";
    var port = 8080;
    var baseApi = "/freqline-be"
    var urlBase = "http://" + address + ":" + port + baseApi;
	urlBase += "/action/login";
    $http.defaults.withCredentials = true;

    service.login = function (username, password) { 
        let url = urlBase + "?t=" + new Date().getTime() + "&username=" + username + "&password=" + password;
		let data = "username=" + username + "&password=" + password;
        return $http.post(url, data, ).then(function (response){
            return response.data;
        },function (error){
            return error;
        });
    };

    service.isLoggedIn = function() {
        let url = urlBase + "?t=" + new Date().getTime();
        let data = "";

        return $http.get(url, data, config).then(function (response) {
            return {response: response.data.isLoggedIn};
        }, function(error) {
            return {response: false};
        });
    }

    service.getPermissions = function() {
        let url = urlBase + "/permissions?t=" + new Date().getTime();
        let data = "";

        return $http.get(url, data, config).then(function (response) {
            return response.data.permissions;
        }, function(error) {
            return error;
        });
    }

    return service;
}]);
