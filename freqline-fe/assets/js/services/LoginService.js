app.factory('LoginService', ['$http', function($http) {
    var service = {};
	var address = "localhost";
    var port = 8080;
    var baseApi = "/freqline-be"
    var urlBase = "http://" + address + ":" + port + baseApi;
	urlBase += "/action/login";

    service.login = function (username, password) { 
        let url = urlBase + "?t=" + new Date().getTime() + "&username=" + username + "&password=" + password;
		let data = "username=" + username + "&password=" + password;
        return $http.post(url, data, {headers: {'Access-Control-Allow-Origin': ' *' } }).then(function (response){
            return response.data;
        },function (error){
            return error;
        });
    };

    return service;
}]);
