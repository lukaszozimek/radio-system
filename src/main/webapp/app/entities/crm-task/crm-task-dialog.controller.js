(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmTaskDialogController', CrmTaskDialogController);

    CrmTaskDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CrmTask', 'CorUser', 'CrmTaskStatus', 'CorNetwork', 'CrmOpportunity', 'CrmContact', 'CrmAccount', 'CrmLead'];

    function CrmTaskDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CrmTask, CorUser, CrmTaskStatus, CorNetwork, CrmOpportunity, CrmContact, CrmAccount, CrmLead) {
        var vm = this;

        vm.crmTask = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.corusers = CorUser.query();
        vm.crmtaskstatuses = CrmTaskStatus.query();
        vm.cornetworks = CorNetwork.query();
        vm.crmopportunities = CrmOpportunity.query();
        vm.crmcontacts = CrmContact.query();
        vm.crmaccounts = CrmAccount.query();
        vm.crmleads = CrmLead.query();
        vm.crmtasks = CrmTask.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.crmTask.id !== null) {
                CrmTask.update(vm.crmTask, onSaveSuccess, onSaveError);
            } else {
                CrmTask.save(vm.crmTask, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:crmTaskUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.activityDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
