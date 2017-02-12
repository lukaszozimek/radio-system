(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibMediaItemDeleteController',LibMediaItemDeleteController);

    LibMediaItemDeleteController.$inject = ['$uibModalInstance', 'entity', 'LibMediaItem'];

    function LibMediaItemDeleteController($uibModalInstance, entity, LibMediaItem) {
        var vm = this;

        vm.libMediaItem = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LibMediaItem.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
