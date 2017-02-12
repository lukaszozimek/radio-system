(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibImageItemDeleteController',LibImageItemDeleteController);

    LibImageItemDeleteController.$inject = ['$uibModalInstance', 'entity', 'LibImageItem'];

    function LibImageItemDeleteController($uibModalInstance, entity, LibImageItem) {
        var vm = this;

        vm.libImageItem = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LibImageItem.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
