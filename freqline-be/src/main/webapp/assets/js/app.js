var app = angular.module('FreqlineAPP', ['ngRoute','ngSanitize']);

app.config(function ($routeProvider) {
    $routeProvider.when('/', {
        templateUrl: 'views/index.html'
    });

    $routeProvider.when('/users', {
        templateUrl: 'views/users.html'
    });

    $routeProvider.when('/user/:id', {
        templateUrl: 'views/user.html',
    });

    $routeProvider.when('/login', {
        templateUrl: 'views/login.html'
    });

    $routeProvider.when('/status', {
        templateUrl: 'views/status.html',
        controller: 'StatusController'
    });

    $routeProvider.when('/401', {
        templateUrl: 'views/errors/401.html',
        controller: 'Error401Controller'
    });

    $routeProvider.when('/404', {
        templateUrl: 'views/errors/404.html',
        controller: 'Error404Controller'
    });

    $routeProvider.otherwise({
        redirectTo: '/404'
    });
}).run(function($rootScope, $route) {
    $rootScope.$route = $route;
});
