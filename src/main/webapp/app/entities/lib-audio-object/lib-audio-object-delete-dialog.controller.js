(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibAudioObjectDeleteController',LibAudioObjectDeleteController);

    LibAudioObjectDeleteController.$inject = ['$uibModalInstance', 'entity', 'LibAudioObject'];

    function LibAudioObjectDeleteController($uibModalInstance, entity, LibAudioObject) {
        var vm = this;

        vm.libAudioObject = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LibAudioObject.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
