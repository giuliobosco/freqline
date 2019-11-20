var app = angular.module('FreqlineAPP', ['ngRoute','ngSanitize']);

app.config(function ($routeProvider) {
    $routeProvider.when('/', {
        templateUrl: 'views/index.html'
    });

    $routeProvider.otherwise({
        redirectTo: '/'
    });
}).run(function($rootScope, $route) {
    $rootScope.$route = $route;
});
