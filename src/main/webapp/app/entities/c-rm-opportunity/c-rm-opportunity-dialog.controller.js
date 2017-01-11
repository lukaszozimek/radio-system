(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CRMOpportunityDialogController', CRMOpportunityDialogController);

    CRMOpportunityDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CRMOpportunity', 'CORNetwork'];

    function CRMOpportunityDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CRMOpportunity, CORNetwork) {
        var vm = this;

        vm.cRMOpportunity = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
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
            if (vm.cRMOpportunity.id !== null) {
                CRMOpportunity.update(vm.cRMOpportunity, onSaveSuccess, onSaveError);
            } else {
                CRMOpportunity.save(vm.cRMOpportunity, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:cRMOpportunityUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.lastTry = false;
        vm.datePickerOpenStatus.closeDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
