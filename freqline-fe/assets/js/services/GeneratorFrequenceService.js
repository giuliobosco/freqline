app.factory('GeneratorFrequenceService', ['$http', function($http) {
    var service = {};
	var address = "localhost";
	var port = 8080;
    var baseApi = "/freqline-be"
    var urlBase = "http://" + address + ":" + port + baseApi;
	urlBase += "/action/generatorFrequence";

    service.setGeneratorFrequence = function (frequence) { 
		let url = urlBase + "?frequence=" + frequence;
        return $http({
            method: 'POST',
            url: url
        }).then(function (response){
            return response.data;
        },function (error){
            return error;
        });
    };

    service.getGeneratorFrequence = function() {
        
        return $http({
            method: 'GET',
            url: urlBase
        }).then(function (response) {
            return response.data;
        }, function(error) {
            return error;
        });
    };

    return service;
}]);
