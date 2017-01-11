(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CRMLeadDeleteController',CRMLeadDeleteController);

    CRMLeadDeleteController.$inject = ['$uibModalInstance', 'entity', 'CRMLead'];

    function CRMLeadDeleteController($uibModalInstance, entity, CRMLead) {
        var vm = this;

        vm.cRMLead = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CRMLead.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
