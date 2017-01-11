(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBTrackDeleteController',LIBTrackDeleteController);

    LIBTrackDeleteController.$inject = ['$uibModalInstance', 'entity', 'LIBTrack'];

    function LIBTrackDeleteController($uibModalInstance, entity, LIBTrack) {
        var vm = this;

        vm.lIBTrack = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LIBTrack.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
