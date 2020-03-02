app.controller('StatusController', ['$scope', '$location', 'GeneratorFrequenceService', 'GeneratorMicService', 'GeneratorStatusService', 'GeneratorDecibelService', function ($scope, $location, generatorFrequenceService, generatorMicService, generatorStatusService, generatorDecibelService) {
    
    $scope.s = {};
    $scope.s.timer = '';
    $scope.s.frequence = '';
    $scope.s.decibel = '';

    $scope.load = function() {
        generatorStatusService.getGeneratorStatus().then(function(data) {
            $scope.status = data.message=="true"?'checked':'';
        });
        
        generatorFrequenceService.getGeneratorFrequence().then(function(data) {
            $scope.s.frequence = parseInt(data.message);
        });
        
        generatorMicService.getMicTimer().then(function(data) {
            $scope.s.timer = parseInt(data.message);
        });

        generatorDecibelService.getDecibel().then(function(data) {
            $scope.s.decibel = parseInt(data.message);
        });
    }
    
    $scope.setStatus = function(status) {
        generatorStatusService.setGeneratorStatus(status).then(function(data) {
            $scope.load();
        });
    }

    $scope.toggleStatus = function() {
        if ($scope.status == "checked") {
            $scope.setStatus(0);
        } else {
            $scope.setStatus(1);
        }
    }

    $scope.save = function() {
        $scope.setTimer($scope.s.timer);
        $scope.setFrequence($scope.s.frequence);
        $scope.setDecibel($scope.s.decibel);
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

    $scope.setDecibel = function(decibel) {
        generatorDecibelService.setDecibel(decibel).then(function(data) {
            $scope.load();
        })
    }

    $scope.load();
}]);