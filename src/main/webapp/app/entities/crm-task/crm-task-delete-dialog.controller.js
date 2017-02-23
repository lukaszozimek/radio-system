(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmTaskDeleteController',CrmTaskDeleteController);

    CrmTaskDeleteController.$inject = ['$uibModalInstance', 'entity', 'CrmTask'];

    function CrmTaskDeleteController($uibModalInstance, entity, CrmTask) {
        var vm = this;

        vm.crmTask = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CrmTask.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
