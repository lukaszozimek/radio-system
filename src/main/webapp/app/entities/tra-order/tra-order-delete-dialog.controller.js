(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraOrderDeleteController',TraOrderDeleteController);

    TraOrderDeleteController.$inject = ['$uibModalInstance', 'entity', 'TraOrder'];

    function TraOrderDeleteController($uibModalInstance, entity, TraOrder) {
        var vm = this;

        vm.traOrder = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TraOrder.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
