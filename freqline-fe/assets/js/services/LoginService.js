app.factory('LoginService', ['$http', function($http) {
    var service = {};
	var address = "localhost";
	var port = 8080;
    var urlBase = "http://" + address + ":" + port;
	urlBase += "/action/login";

    service.login = function (username, password) { 
		let url = urlBase + "?username=" + username + "&password=" + password;
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
