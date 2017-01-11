(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CRMStageDetailController', CRMStageDetailController);

    CRMStageDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CRMStage', 'CORNetwork'];

    function CRMStageDetailController($scope, $rootScope, $stateParams, previousState, entity, CRMStage, CORNetwork) {
        var vm = this;

        vm.cRMStage = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:cRMStageUpdate', function(event, result) {
            vm.cRMStage = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
