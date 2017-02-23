(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmLeadSourceDialogController', CrmLeadSourceDialogController);

    CrmLeadSourceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CrmLeadSource', 'CorNetwork'];

    function CrmLeadSourceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CrmLeadSource, CorNetwork) {
        var vm = this;

        vm.crmLeadSource = entity;
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
            if (vm.crmLeadSource.id !== null) {
                CrmLeadSource.update(vm.crmLeadSource, onSaveSuccess, onSaveError);
            } else {
                CrmLeadSource.save(vm.crmLeadSource, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:crmLeadSourceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
