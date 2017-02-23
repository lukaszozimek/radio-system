(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibLibraryDeleteController',LibLibraryDeleteController);

    LibLibraryDeleteController.$inject = ['$uibModalInstance', 'entity', 'LibLibrary'];

    function LibLibraryDeleteController($uibModalInstance, entity, LibLibrary) {
        var vm = this;

        vm.libLibrary = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LibLibrary.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
