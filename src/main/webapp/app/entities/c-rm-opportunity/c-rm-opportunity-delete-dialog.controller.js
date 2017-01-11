(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CRMOpportunityDeleteController',CRMOpportunityDeleteController);

    CRMOpportunityDeleteController.$inject = ['$uibModalInstance', 'entity', 'CRMOpportunity'];

    function CRMOpportunityDeleteController($uibModalInstance, entity, CRMOpportunity) {
        var vm = this;

        vm.cRMOpportunity = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CRMOpportunity.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
