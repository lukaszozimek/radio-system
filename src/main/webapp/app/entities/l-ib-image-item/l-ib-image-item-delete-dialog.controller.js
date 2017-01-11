(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBImageItemDeleteController',LIBImageItemDeleteController);

    LIBImageItemDeleteController.$inject = ['$uibModalInstance', 'entity', 'LIBImageItem'];

    function LIBImageItemDeleteController($uibModalInstance, entity, LIBImageItem) {
        var vm = this;

        vm.lIBImageItem = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LIBImageItem.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
