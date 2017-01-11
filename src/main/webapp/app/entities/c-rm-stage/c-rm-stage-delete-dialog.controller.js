(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CRMStageDeleteController',CRMStageDeleteController);

    CRMStageDeleteController.$inject = ['$uibModalInstance', 'entity', 'CRMStage'];

    function CRMStageDeleteController($uibModalInstance, entity, CRMStage) {
        var vm = this;

        vm.cRMStage = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CRMStage.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
