(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmLeadSourceDeleteController',CrmLeadSourceDeleteController);

    CrmLeadSourceDeleteController.$inject = ['$uibModalInstance', 'entity', 'CrmLeadSource'];

    function CrmLeadSourceDeleteController($uibModalInstance, entity, CrmLeadSource) {
        var vm = this;

        vm.crmLeadSource = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CrmLeadSource.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
