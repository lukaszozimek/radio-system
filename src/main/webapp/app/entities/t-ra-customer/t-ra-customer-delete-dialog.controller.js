(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TRACustomerDeleteController',TRACustomerDeleteController);

    TRACustomerDeleteController.$inject = ['$uibModalInstance', 'entity', 'TRACustomer'];

    function TRACustomerDeleteController($uibModalInstance, entity, TRACustomer) {
        var vm = this;

        vm.tRACustomer = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TRACustomer.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
