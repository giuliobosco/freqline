app.factory('GeneratorFrequenceService', ['$http', 'baseUrl', function($http, baseUrl) {
    var config = {headers: [{'Access-Control-Allow-Origin': ' *' },{'Access-Control-Allow-Credentials':'true'}]};
    var service = {};
    var serviceUrl = baseUrl + "/action/generatorFrequence";
    $http.defaults.withCredentials = true;
    
    service.getGeneratorFrequence = function() {
        let url = serviceUrl + "?t=" + new Date().getTime();
        
        let data = {};
        
        return $http.get(url, data, config).then(function (response) {
            return response.data;
        }, function(error) {
            if (error.status == 401) {
                $.notify("Operation not permitted", "error")
            } else if (error.status == 404) {
                $rootScope.error = error;
                $location.path('/404')
            } else {
                return error;
            }
        })
    };
    
    service.setGeneratorFrequence = function (frequence) { 
        let url = serviceUrl + "?frequence=" + frequence;
        let data = 't=' + new Date().getTime();
        
        return $http.post(url, data, config).then(function (response){
            return response.data;
        },function (error){
            if (error.status == 401) {
                $.notify("Operation not permitted", "error")
            } else if (error.status == 404) {
                $rootScope.error = error;
                $location.path('/404')
            } else {
                return error.data;
            }
        });
    };
    
    return service;
}]);
