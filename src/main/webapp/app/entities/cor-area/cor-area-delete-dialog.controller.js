(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorAreaDeleteController',CorAreaDeleteController);

    CorAreaDeleteController.$inject = ['$uibModalInstance', 'entity', 'CorArea'];

    function CorAreaDeleteController($uibModalInstance, entity, CorArea) {
        var vm = this;

        vm.corArea = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CorArea.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
