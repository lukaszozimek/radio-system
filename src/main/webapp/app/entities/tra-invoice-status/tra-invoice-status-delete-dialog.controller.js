(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraInvoiceStatusDeleteController',TraInvoiceStatusDeleteController);

    TraInvoiceStatusDeleteController.$inject = ['$uibModalInstance', 'entity', 'TraInvoiceStatus'];

    function TraInvoiceStatusDeleteController($uibModalInstance, entity, TraInvoiceStatus) {
        var vm = this;

        vm.traInvoiceStatus = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TraInvoiceStatus.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
