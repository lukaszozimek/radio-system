(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraDiscountDeleteController',TraDiscountDeleteController);

    TraDiscountDeleteController.$inject = ['$uibModalInstance', 'entity', 'TraDiscount'];

    function TraDiscountDeleteController($uibModalInstance, entity, TraDiscount) {
        var vm = this;

        vm.traDiscount = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TraDiscount.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
