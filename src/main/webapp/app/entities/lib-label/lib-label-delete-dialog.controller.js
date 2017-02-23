(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibLabelDeleteController',LibLabelDeleteController);

    LibLabelDeleteController.$inject = ['$uibModalInstance', 'entity', 'LibLabel'];

    function LibLabelDeleteController($uibModalInstance, entity, LibLabel) {
        var vm = this;

        vm.libLabel = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LibLabel.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
