(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibMarkerDeleteController',LibMarkerDeleteController);

    LibMarkerDeleteController.$inject = ['$uibModalInstance', 'entity', 'LibMarker'];

    function LibMarkerDeleteController($uibModalInstance, entity, LibMarker) {
        var vm = this;

        vm.libMarker = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LibMarker.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
