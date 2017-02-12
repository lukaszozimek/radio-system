(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmOpportunityDialogController', CrmOpportunityDialogController);

    CrmOpportunityDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CrmOpportunity', 'CrmStage', 'CorUser', 'CrmContact', 'CrmAccount', 'CrmLead', 'CorNetwork', 'CrmTask'];

    function CrmOpportunityDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CrmOpportunity, CrmStage, CorUser, CrmContact, CrmAccount, CrmLead, CorNetwork, CrmTask) {
        var vm = this;

        vm.crmOpportunity = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.crmstages = CrmStage.query();
        vm.corusers = CorUser.query();
        vm.crmcontacts = CrmContact.query();
        vm.crmaccounts = CrmAccount.query();
        vm.crmleads = CrmLead.query();
        vm.cornetworks = CorNetwork.query();
        vm.crmtasks = CrmTask.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.crmOpportunity.id !== null) {
                CrmOpportunity.update(vm.crmOpportunity, onSaveSuccess, onSaveError);
            } else {
                CrmOpportunity.save(vm.crmOpportunity, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:crmOpportunityUpdate', result);
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
