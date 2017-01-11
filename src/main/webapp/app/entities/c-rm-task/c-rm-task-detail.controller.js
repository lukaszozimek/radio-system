(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CRMTaskDetailController', CRMTaskDetailController);

    CRMTaskDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CRMTask', 'CORNetwork'];

    function CRMTaskDetailController($scope, $rootScope, $stateParams, previousState, entity, CRMTask, CORNetwork) {
        var vm = this;

        vm.cRMTask = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:cRMTaskUpdate', function(event, result) {
            vm.cRMTask = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
