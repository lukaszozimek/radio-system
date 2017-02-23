(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraCampaignDialogController', TraCampaignDialogController);

    TraCampaignDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TraCampaign', 'CrmAccount', 'CorNetwork', 'TraCampaingStatus', 'TraPrice', 'TraOrder', 'SchEmission'];

    function TraCampaignDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TraCampaign, CrmAccount, CorNetwork, TraCampaingStatus, TraPrice, TraOrder, SchEmission) {
        var vm = this;

        vm.traCampaign = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.crmaccounts = CrmAccount.query();
        vm.cornetworks = CorNetwork.query();
        vm.tracampaingstatuses = TraCampaingStatus.query();
        vm.traprices = TraPrice.query();
        vm.traorders = TraOrder.query();
        vm.schemissions = SchEmission.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.traCampaign.id !== null) {
                TraCampaign.update(vm.traCampaign, onSaveSuccess, onSaveError);
            } else {
                TraCampaign.save(vm.traCampaign, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:traCampaignUpdate', result);
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
