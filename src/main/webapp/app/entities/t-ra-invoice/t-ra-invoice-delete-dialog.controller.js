(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TRAInvoiceDeleteController',TRAInvoiceDeleteController);

    TRAInvoiceDeleteController.$inject = ['$uibModalInstance', 'entity', 'TRAInvoice'];

    function TRAInvoiceDeleteController($uibModalInstance, entity, TRAInvoice) {
        var vm = this;

        vm.tRAInvoice = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TRAInvoice.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
