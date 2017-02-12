(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorTaxDeleteController',CorTaxDeleteController);

    CorTaxDeleteController.$inject = ['$uibModalInstance', 'entity', 'CorTax'];

    function CorTaxDeleteController($uibModalInstance, entity, CorTax) {
        var vm = this;

        vm.corTax = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CorTax.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
