(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibImageObjectDeleteController',LibImageObjectDeleteController);

    LibImageObjectDeleteController.$inject = ['$uibModalInstance', 'entity', 'LibImageObject'];

    function LibImageObjectDeleteController($uibModalInstance, entity, LibImageObject) {
        var vm = this;

        vm.libImageObject = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LibImageObject.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
