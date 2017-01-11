(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBCloudObjectDeleteController',LIBCloudObjectDeleteController);

    LIBCloudObjectDeleteController.$inject = ['$uibModalInstance', 'entity', 'LIBCloudObject'];

    function LIBCloudObjectDeleteController($uibModalInstance, entity, LIBCloudObject) {
        var vm = this;

        vm.lIBCloudObject = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LIBCloudObject.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
