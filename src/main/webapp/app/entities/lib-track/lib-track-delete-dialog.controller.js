(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibTrackDeleteController',LibTrackDeleteController);

    LibTrackDeleteController.$inject = ['$uibModalInstance', 'entity', 'LibTrack'];

    function LibTrackDeleteController($uibModalInstance, entity, LibTrack) {
        var vm = this;

        vm.libTrack = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LibTrack.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
