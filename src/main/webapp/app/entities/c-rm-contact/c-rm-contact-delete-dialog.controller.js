(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CRMContactDeleteController',CRMContactDeleteController);

    CRMContactDeleteController.$inject = ['$uibModalInstance', 'entity', 'CRMContact'];

    function CRMContactDeleteController($uibModalInstance, entity, CRMContact) {
        var vm = this;

        vm.cRMContact = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CRMContact.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
