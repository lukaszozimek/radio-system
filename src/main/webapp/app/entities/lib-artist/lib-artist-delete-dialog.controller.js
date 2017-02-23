(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibArtistDeleteController',LibArtistDeleteController);

    LibArtistDeleteController.$inject = ['$uibModalInstance', 'entity', 'LibArtist'];

    function LibArtistDeleteController($uibModalInstance, entity, LibArtist) {
        var vm = this;

        vm.libArtist = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LibArtist.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
