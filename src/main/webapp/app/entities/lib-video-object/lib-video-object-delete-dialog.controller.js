(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibVideoObjectDeleteController',LibVideoObjectDeleteController);

    LibVideoObjectDeleteController.$inject = ['$uibModalInstance', 'entity', 'LibVideoObject'];

    function LibVideoObjectDeleteController($uibModalInstance, entity, LibVideoObject) {
        var vm = this;

        vm.libVideoObject = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LibVideoObject.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
