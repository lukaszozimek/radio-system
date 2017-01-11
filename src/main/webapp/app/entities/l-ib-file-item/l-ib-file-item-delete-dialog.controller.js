(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBFileItemDeleteController',LIBFileItemDeleteController);

    LIBFileItemDeleteController.$inject = ['$uibModalInstance', 'entity', 'LIBFileItem'];

    function LIBFileItemDeleteController($uibModalInstance, entity, LIBFileItem) {
        var vm = this;

        vm.lIBFileItem = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LIBFileItem.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
