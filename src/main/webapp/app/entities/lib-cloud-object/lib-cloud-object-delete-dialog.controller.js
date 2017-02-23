(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibCloudObjectDeleteController',LibCloudObjectDeleteController);

    LibCloudObjectDeleteController.$inject = ['$uibModalInstance', 'entity', 'LibCloudObject'];

    function LibCloudObjectDeleteController($uibModalInstance, entity, LibCloudObject) {
        var vm = this;

        vm.libCloudObject = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LibCloudObject.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
