app.factory('GroupService', ['$http', 'UserGroupService', function($http, userGroupService) {
    var config = {headers: [{'Access-Control-Allow-Origin': ' *' },{'Access-Control-Allow-Credentials':'true'}]};
    var service = {};
    var address = "localhost";
    var port = 8080;
    var baseApi = "/freqline-be"
    var urlBase = "http://" + address + ":" + port + baseApi;
    urlBase += "/data/group";
    $http.defaults.withCredentials = true;
    
    service.getAll = function() {
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
    
    service.getById = function (id) { 
        let url = urlBase + "/" + id;
        let data = 't=' + new Date().getTime();
        
        return $http.get(url, data, config).then(function (response){
            return response.data;
        },function (error){
            if (error.status == 401) {
                $rootScope.error = error;
                $location.path('/401');
                return error;
            } else if (error.status == 404) {
                $rootScope.error = error;
                $location.path('/404');
            } else {
                return error.data;
            }
        });
    };
    
    service.getByUserId = function(userId) {
        return userGroupService.getAll().then(function(data) {
            let userGroups = data.data;
            let group = null;

            userGroups.forEach(element => {
                if (element.user == userId) {
                    group = element
                }
            });
            
            return group;
        })
    };
    
    return service;
}]);
