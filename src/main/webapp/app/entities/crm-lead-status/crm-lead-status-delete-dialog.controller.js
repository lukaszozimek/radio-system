(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmLeadStatusDeleteController',CrmLeadStatusDeleteController);

    CrmLeadStatusDeleteController.$inject = ['$uibModalInstance', 'entity', 'CrmLeadStatus'];

    function CrmLeadStatusDeleteController($uibModalInstance, entity, CrmLeadStatus) {
        var vm = this;

        vm.crmLeadStatus = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CrmLeadStatus.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
