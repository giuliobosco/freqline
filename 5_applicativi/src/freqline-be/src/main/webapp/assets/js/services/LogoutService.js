app.factory('LogoutService', ['$http', 'baseUrl', function($http, baseUrl) {
    var config = {headers: [{'Access-Control-Allow-Origin': ' *' },{'Access-Control-Allow-Credentials':'true'}]};
    var service = {};
    var serviceUrl = baseUrl + "/action/logout";
    
    service.logout = function () { 
        let data = {};
        return $http.get(serviceUrl, data, config).then(function (response){
            return response;
        },function (error){
            return error;
        });
    };
    
    return service;
}]);
