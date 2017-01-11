(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBLibraryDeleteController',LIBLibraryDeleteController);

    LIBLibraryDeleteController.$inject = ['$uibModalInstance', 'entity', 'LIBLibrary'];

    function LIBLibraryDeleteController($uibModalInstance, entity, LIBLibrary) {
        var vm = this;

        vm.lIBLibrary = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LIBLibrary.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
