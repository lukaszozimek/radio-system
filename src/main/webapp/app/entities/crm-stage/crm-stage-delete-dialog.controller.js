(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmStageDeleteController',CrmStageDeleteController);

    CrmStageDeleteController.$inject = ['$uibModalInstance', 'entity', 'CrmStage'];

    function CrmStageDeleteController($uibModalInstance, entity, CrmStage) {
        var vm = this;

        vm.crmStage = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CrmStage.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
