(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBImageObjectDeleteController',LIBImageObjectDeleteController);

    LIBImageObjectDeleteController.$inject = ['$uibModalInstance', 'entity', 'LIBImageObject'];

    function LIBImageObjectDeleteController($uibModalInstance, entity, LIBImageObject) {
        var vm = this;

        vm.lIBImageObject = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LIBImageObject.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
