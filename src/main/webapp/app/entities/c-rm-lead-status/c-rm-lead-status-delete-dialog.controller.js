(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CRMLeadStatusDeleteController',CRMLeadStatusDeleteController);

    CRMLeadStatusDeleteController.$inject = ['$uibModalInstance', 'entity', 'CRMLeadStatus'];

    function CRMLeadStatusDeleteController($uibModalInstance, entity, CRMLeadStatus) {
        var vm = this;

        vm.cRMLeadStatus = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CRMLeadStatus.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
