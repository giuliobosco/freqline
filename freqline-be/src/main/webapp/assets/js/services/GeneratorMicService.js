app.factory('GeneratorMicService', ['$http', function($http) {
    var config = {headers: [{'Access-Control-Allow-Origin': ' *' },{'Access-Control-Allow-Credentials':'true'}]};
    var service = {};
    var address = "localhost";
    var port = 8080;
    var baseApi = "/freqline-be"
    var urlBase = "http://" + address + ":" + port + baseApi;
    urlBase += "/action/generatorMicTimer";
    $http.defaults.withCredentials = true;
    
    service.getMicTimer = function() {
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
    
    service.setMicTimer = function (timer) { 
        let url = urlBase + "?timer=" + timer;
        console.log(url);
        let data = 't=' + new Date().getTime();
        
        return $http.post(url, data, config).then(function (response){
            return response.data;
        },function (error){
            if (error.status == 401) {
                $rootScope.error = error;
                $location.path('/401');
                return error;
            } else {
                return error.data;
            }
        });
    };
    
    return service;
}]);
