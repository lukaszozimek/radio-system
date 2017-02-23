(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmStageDialogController', CrmStageDialogController);

    CrmStageDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CrmStage', 'CorNetwork'];

    function CrmStageDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CrmStage, CorNetwork) {
        var vm = this;

        vm.crmStage = entity;
        vm.clear = clear;
        vm.save = save;
        vm.cornetworks = CorNetwork.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.crmStage.id !== null) {
                CrmStage.update(vm.crmStage, onSaveSuccess, onSaveError);
            } else {
                CrmStage.save(vm.crmStage, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:crmStageUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
