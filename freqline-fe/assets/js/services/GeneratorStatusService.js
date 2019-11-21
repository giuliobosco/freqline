app.factory('GeneratorStatusService', ['$http', function($http) {
    var service = {};
	var address = "localhost";
	var port = 8080;
    var baseApi = "/freqline-be"
    var urlBase = "http://" + address + ":" + port + baseApi;
	urlBase += "/action/generatorStatus";

    service.setGeneratorStatus = function (status) { 
		let url = urlBase + "?status=" + status;
        return $http({
            method: 'POST',
            url: url
        }).then(function (response){
            return response.data;
        },function (error){
            return error;
        });
    };

    service.generatorOn = function() {
        service.setGeneratorStatus(1);
    };

    service.generatorOff = function() {
        service.setGeneratorStatus(0);
    };

    service.getGeneratorStatus = function() {
        return $http({
            method: 'GET',
            url: urlBase
        }).then(function(response) {
            return response.data;
        }, function(error) {
            return error;
        });
    };

    return service;
}]);
