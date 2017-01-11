(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TRAPriceDeleteController',TRAPriceDeleteController);

    TRAPriceDeleteController.$inject = ['$uibModalInstance', 'entity', 'TRAPrice'];

    function TRAPriceDeleteController($uibModalInstance, entity, TRAPrice) {
        var vm = this;

        vm.tRAPrice = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TRAPrice.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
