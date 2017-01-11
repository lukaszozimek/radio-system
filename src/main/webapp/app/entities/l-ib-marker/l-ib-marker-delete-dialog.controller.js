(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBMarkerDeleteController',LIBMarkerDeleteController);

    LIBMarkerDeleteController.$inject = ['$uibModalInstance', 'entity', 'LIBMarker'];

    function LIBMarkerDeleteController($uibModalInstance, entity, LIBMarker) {
        var vm = this;

        vm.lIBMarker = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LIBMarker.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
