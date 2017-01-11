(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TRADiscountDeleteController',TRADiscountDeleteController);

    TRADiscountDeleteController.$inject = ['$uibModalInstance', 'entity', 'TRADiscount'];

    function TRADiscountDeleteController($uibModalInstance, entity, TRADiscount) {
        var vm = this;

        vm.tRADiscount = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TRADiscount.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
