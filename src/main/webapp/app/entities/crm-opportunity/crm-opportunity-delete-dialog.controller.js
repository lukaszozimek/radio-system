(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmOpportunityDeleteController',CrmOpportunityDeleteController);

    CrmOpportunityDeleteController.$inject = ['$uibModalInstance', 'entity', 'CrmOpportunity'];

    function CrmOpportunityDeleteController($uibModalInstance, entity, CrmOpportunity) {
        var vm = this;

        vm.crmOpportunity = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CrmOpportunity.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
