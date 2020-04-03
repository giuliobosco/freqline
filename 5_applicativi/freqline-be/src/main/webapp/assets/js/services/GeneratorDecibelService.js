app.factory('GeneratorDecibelService', ['$http', 'baseUrl', function($http, baseUrl) {
    var config = {headers: [{'Access-Control-Allow-Origin': ' *' },{'Access-Control-Allow-Credentials':'true'}]};
    var service = {};
    var serviceUrl = baseUrl + "/action/generatorDecibel";
    $http.defaults.withCredentials = true;
    
    service.getDecibel = function() {
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
    
    service.setDecibel = function (decibel) { 
        let url = serviceUrl + "?decibel=" + decibel;
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
