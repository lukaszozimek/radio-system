(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmTaskStatusDeleteController',CrmTaskStatusDeleteController);

    CrmTaskStatusDeleteController.$inject = ['$uibModalInstance', 'entity', 'CrmTaskStatus'];

    function CrmTaskStatusDeleteController($uibModalInstance, entity, CrmTaskStatus) {
        var vm = this;

        vm.crmTaskStatus = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CrmTaskStatus.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
