(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBLabelDeleteController',LIBLabelDeleteController);

    LIBLabelDeleteController.$inject = ['$uibModalInstance', 'entity', 'LIBLabel'];

    function LIBLabelDeleteController($uibModalInstance, entity, LIBLabel) {
        var vm = this;

        vm.lIBLabel = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LIBLabel.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
