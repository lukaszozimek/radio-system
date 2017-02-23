(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraOrderStatusDeleteController',TraOrderStatusDeleteController);

    TraOrderStatusDeleteController.$inject = ['$uibModalInstance', 'entity', 'TraOrderStatus'];

    function TraOrderStatusDeleteController($uibModalInstance, entity, TraOrderStatus) {
        var vm = this;

        vm.traOrderStatus = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TraOrderStatus.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
