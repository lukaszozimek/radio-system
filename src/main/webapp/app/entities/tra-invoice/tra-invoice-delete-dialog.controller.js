(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraInvoiceDeleteController',TraInvoiceDeleteController);

    TraInvoiceDeleteController.$inject = ['$uibModalInstance', 'entity', 'TraInvoice'];

    function TraInvoiceDeleteController($uibModalInstance, entity, TraInvoice) {
        var vm = this;

        vm.traInvoice = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TraInvoice.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
