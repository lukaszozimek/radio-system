(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmLeadStatusDialogController', CrmLeadStatusDialogController);

    CrmLeadStatusDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CrmLeadStatus', 'CorNetwork'];

    function CrmLeadStatusDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CrmLeadStatus, CorNetwork) {
        var vm = this;

        vm.crmLeadStatus = entity;
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
            if (vm.crmLeadStatus.id !== null) {
                CrmLeadStatus.update(vm.crmLeadStatus, onSaveSuccess, onSaveError);
            } else {
                CrmLeadStatus.save(vm.crmLeadStatus, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:crmLeadStatusUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
