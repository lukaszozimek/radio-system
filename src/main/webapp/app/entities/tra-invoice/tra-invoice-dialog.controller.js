(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraInvoiceDialogController', TraInvoiceDialogController);

    TraInvoiceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TraInvoice', 'CrmAccount', 'CorNetwork', 'TraInvoiceStatus', 'TraOrder'];

    function TraInvoiceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TraInvoice, CrmAccount, CorNetwork, TraInvoiceStatus, TraOrder) {
        var vm = this;

        vm.traInvoice = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.crmaccounts = CrmAccount.query();
        vm.cornetworks = CorNetwork.query();
        vm.trainvoicestatuses = TraInvoiceStatus.query();
        vm.traorders = TraOrder.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.traInvoice.id !== null) {
                TraInvoice.update(vm.traInvoice, onSaveSuccess, onSaveError);
            } else {
                TraInvoice.save(vm.traInvoice, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:traInvoiceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.paymentDay = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
