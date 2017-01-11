(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CRMStageDialogController', CRMStageDialogController);

    CRMStageDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CRMStage', 'CORNetwork'];

    function CRMStageDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CRMStage, CORNetwork) {
        var vm = this;

        vm.cRMStage = entity;
        vm.clear = clear;
        vm.save = save;
        vm.cornetworks = CORNetwork.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.cRMStage.id !== null) {
                CRMStage.update(vm.cRMStage, onSaveSuccess, onSaveError);
            } else {
                CRMStage.save(vm.cRMStage, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:cRMStageUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
