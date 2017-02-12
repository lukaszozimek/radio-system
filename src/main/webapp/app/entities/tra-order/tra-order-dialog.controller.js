(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraOrderDialogController', TraOrderDialogController);

    TraOrderDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TraOrder', 'CrmAccount', 'TraCampaign', 'TraPrice', 'CorNetwork', 'TraOrderStatus', 'TraInvoice'];

    function TraOrderDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TraOrder, CrmAccount, TraCampaign, TraPrice, CorNetwork, TraOrderStatus, TraInvoice) {
        var vm = this;

        vm.traOrder = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.crmaccounts = CrmAccount.query();
        vm.tracampaigns = TraCampaign.query();
        vm.traprices = TraPrice.query();
        vm.cornetworks = CorNetwork.query();
        vm.traorderstatuses = TraOrderStatus.query();
        vm.trainvoices = TraInvoice.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.traOrder.id !== null) {
                TraOrder.update(vm.traOrder, onSaveSuccess, onSaveError);
            } else {
                TraOrder.save(vm.traOrder, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:traOrderUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.startDate = false;
        vm.datePickerOpenStatus.endDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
