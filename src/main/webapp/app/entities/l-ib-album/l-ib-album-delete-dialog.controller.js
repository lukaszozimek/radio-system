(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBAlbumDeleteController',LIBAlbumDeleteController);

    LIBAlbumDeleteController.$inject = ['$uibModalInstance', 'entity', 'LIBAlbum'];

    function LIBAlbumDeleteController($uibModalInstance, entity, LIBAlbum) {
        var vm = this;

        vm.lIBAlbum = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LIBAlbum.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
