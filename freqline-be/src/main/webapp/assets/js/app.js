var app = angular.module('FreqlineAPP', ['ngRoute','ngSanitize']);

app.config(function ($routeProvider) {
    $routeProvider.when('/', {
        templateUrl: 'views/index.html'
    });

    $routeProvider.when('/users', {
        templateUrl: 'views/users.html'
    });

    $routeProvider.when('/login', {
        templateUrl: 'views/login.html'
    });

    $routeProvider.when('/401', {
        templateUrl: 'views/errors/401.html',
        controller: 'Error401Controller'
    });

    $routeProvider.otherwise({
        templateUrl: 'views/errors/404.html',
        controller: 'Error404Controller'
    });
}).run(function($rootScope, $route) {
    $rootScope.$route = $route;
});
