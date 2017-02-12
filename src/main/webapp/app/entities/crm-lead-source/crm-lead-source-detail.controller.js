(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmLeadSourceDetailController', CrmLeadSourceDetailController);

    CrmLeadSourceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CrmLeadSource', 'CorNetwork'];

    function CrmLeadSourceDetailController($scope, $rootScope, $stateParams, previousState, entity, CrmLeadSource, CorNetwork) {
        var vm = this;

        vm.crmLeadSource = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:crmLeadSourceUpdate', function(event, result) {
            vm.crmLeadSource = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
