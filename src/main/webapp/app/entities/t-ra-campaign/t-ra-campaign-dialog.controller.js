(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TRACampaignDialogController', TRACampaignDialogController);

    TRACampaignDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TRACampaign', 'TRACustomer'];

    function TRACampaignDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TRACampaign, TRACustomer) {
        var vm = this;

        vm.tRACampaign = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.tracustomers = TRACustomer.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tRACampaign.id !== null) {
                TRACampaign.update(vm.tRACampaign, onSaveSuccess, onSaveError);
            } else {
                TRACampaign.save(vm.tRACampaign, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:tRACampaignUpdate', result);
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
