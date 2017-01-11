(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CRMTaskStatusDetailController', CRMTaskStatusDetailController);

    CRMTaskStatusDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CRMTaskStatus', 'CORNetwork'];

    function CRMTaskStatusDetailController($scope, $rootScope, $stateParams, previousState, entity, CRMTaskStatus, CORNetwork) {
        var vm = this;

        vm.cRMTaskStatus = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:cRMTaskStatusUpdate', function(event, result) {
            vm.cRMTaskStatus = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
