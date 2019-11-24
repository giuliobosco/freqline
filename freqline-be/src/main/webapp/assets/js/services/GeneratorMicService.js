app.factory('GeneratorMicService', ['$http', function($http) {
    var service = {};
	var address = "localhost";
	var port = 8080;
    var baseApi = "/freqline-be"
    var urlBase = "http://" + address + ":" + port + baseApi;
	urlBase += "/action/generatorMicTimer";

    service.setGeneratorMic = function (timer) { 
		let url = urlBase + "?timer=" + timer;
        return $http({
            method: 'POST',
            url: url
        }).then(function (response){
            return response.data;
        },function (error){
            return error;
        });
    };

    service.getGeneratorMic = function() {
        return $http({
            method: 'GET',
            url: urlBase
        }).then(function (response) {
            return response.data;
        }, function (error) {
            return error
        });
    };

    return service;
}]);