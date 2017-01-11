(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CRMLeadSourceDeleteController',CRMLeadSourceDeleteController);

    CRMLeadSourceDeleteController.$inject = ['$uibModalInstance', 'entity', 'CRMLeadSource'];

    function CRMLeadSourceDeleteController($uibModalInstance, entity, CRMLeadSource) {
        var vm = this;

        vm.cRMLeadSource = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CRMLeadSource.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
