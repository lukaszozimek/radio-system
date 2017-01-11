(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TRAEmissionOrderDeleteController',TRAEmissionOrderDeleteController);

    TRAEmissionOrderDeleteController.$inject = ['$uibModalInstance', 'entity', 'TRAEmissionOrder'];

    function TRAEmissionOrderDeleteController($uibModalInstance, entity, TRAEmissionOrder) {
        var vm = this;

        vm.tRAEmissionOrder = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TRAEmissionOrder.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
