app.controller('StatusController', ['$scope', '$location', 'GeneratorFrequenceService', 'GeneratorMicService', 'GeneratorStatusService', function ($scope, $location, generatorFrequenceService, generatorMicService, generatorStatusService) {
    
    $scope.load = function() {
        generatorStatusService.getGeneratorStatus().then(function(data) {
            $scope.status = data.message;
        });
        
        generatorFrequenceService.getGeneratorFrequence().then(function(data) {
            $scope.frequence = data.message;
        });
        
        generatorMicService.getMicTimer().then(function(data) {
            $scope.timer = data.message;
        });
    }
    
    $scope.setStatus = function(status) {
        generatorStatusService.setGeneratorStatus(status).then(function(data) {
            $scope.load();
        });
    }

    $scope.setTimer = function(timer) {
        generatorMicService.setMicTimer(timer).then(function(data) {
            $scope.load();
        });
    }

    $scope.setFrequence = function(frequence) {
        generatorFrequenceService.setGeneratorFrequence(frequence).then(function(data) {
            $scope.load();
        });
    }

    $scope.load();
}]);