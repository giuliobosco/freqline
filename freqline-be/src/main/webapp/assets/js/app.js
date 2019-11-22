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

    $routeProvider.otherwise({
        redirectTo: '/'
    });
}).run(function($rootScope, $route) {
    $rootScope.$route = $route;
});
