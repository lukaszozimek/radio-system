(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('SchBlockDeleteController',SchBlockDeleteController);

    SchBlockDeleteController.$inject = ['$uibModalInstance', 'entity', 'SchBlock'];

    function SchBlockDeleteController($uibModalInstance, entity, SchBlock) {
        var vm = this;

        vm.schBlock = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SchBlock.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
