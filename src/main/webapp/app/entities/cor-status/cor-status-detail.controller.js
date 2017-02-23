(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorStatusDetailController', CorStatusDetailController);

    CorStatusDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CorStatus'];

    function CorStatusDetailController($scope, $rootScope, $stateParams, previousState, entity, CorStatus) {
        var vm = this;

        vm.corStatus = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:corStatusUpdate', function(event, result) {
            vm.corStatus = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
