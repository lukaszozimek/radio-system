(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CRMTaskDeleteController',CRMTaskDeleteController);

    CRMTaskDeleteController.$inject = ['$uibModalInstance', 'entity', 'CRMTask'];

    function CRMTaskDeleteController($uibModalInstance, entity, CRMTask) {
        var vm = this;

        vm.cRMTask = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CRMTask.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
