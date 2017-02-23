(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmLeadDeleteController',CrmLeadDeleteController);

    CrmLeadDeleteController.$inject = ['$uibModalInstance', 'entity', 'CrmLead'];

    function CrmLeadDeleteController($uibModalInstance, entity, CrmLead) {
        var vm = this;

        vm.crmLead = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CrmLead.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
