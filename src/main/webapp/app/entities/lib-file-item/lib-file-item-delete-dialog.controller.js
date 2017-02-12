(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibFileItemDeleteController',LibFileItemDeleteController);

    LibFileItemDeleteController.$inject = ['$uibModalInstance', 'entity', 'LibFileItem'];

    function LibFileItemDeleteController($uibModalInstance, entity, LibFileItem) {
        var vm = this;

        vm.libFileItem = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LibFileItem.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
