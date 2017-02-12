(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmStageDetailController', CrmStageDetailController);

    CrmStageDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CrmStage', 'CorNetwork'];

    function CrmStageDetailController($scope, $rootScope, $stateParams, previousState, entity, CrmStage, CorNetwork) {
        var vm = this;

        vm.crmStage = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:crmStageUpdate', function(event, result) {
            vm.crmStage = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
