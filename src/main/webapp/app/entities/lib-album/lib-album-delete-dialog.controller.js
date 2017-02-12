(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibAlbumDeleteController',LibAlbumDeleteController);

    LibAlbumDeleteController.$inject = ['$uibModalInstance', 'entity', 'LibAlbum'];

    function LibAlbumDeleteController($uibModalInstance, entity, LibAlbum) {
        var vm = this;

        vm.libAlbum = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LibAlbum.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
