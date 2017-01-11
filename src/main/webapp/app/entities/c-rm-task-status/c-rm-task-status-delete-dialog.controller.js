(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CRMTaskStatusDeleteController',CRMTaskStatusDeleteController);

    CRMTaskStatusDeleteController.$inject = ['$uibModalInstance', 'entity', 'CRMTaskStatus'];

    function CRMTaskStatusDeleteController($uibModalInstance, entity, CRMTaskStatus) {
        var vm = this;

        vm.cRMTaskStatus = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CRMTaskStatus.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
