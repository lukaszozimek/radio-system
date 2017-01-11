(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TRAOrderDeleteController',TRAOrderDeleteController);

    TRAOrderDeleteController.$inject = ['$uibModalInstance', 'entity', 'TRAOrder'];

    function TRAOrderDeleteController($uibModalInstance, entity, TRAOrder) {
        var vm = this;

        vm.tRAOrder = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TRAOrder.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
