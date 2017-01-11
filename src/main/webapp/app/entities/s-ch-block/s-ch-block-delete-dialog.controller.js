(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('SCHBlockDeleteController',SCHBlockDeleteController);

    SCHBlockDeleteController.$inject = ['$uibModalInstance', 'entity', 'SCHBlock'];

    function SCHBlockDeleteController($uibModalInstance, entity, SCHBlock) {
        var vm = this;

        vm.sCHBlock = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SCHBlock.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
