(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TRAOrderDialogController', TRAOrderDialogController);

    TRAOrderDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TRAOrder', 'TRACampaign', 'TRACustomer'];

    function TRAOrderDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TRAOrder, TRACampaign, TRACustomer) {
        var vm = this;

        vm.tRAOrder = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.tracampaigns = TRACampaign.query();
        vm.tracustomers = TRACustomer.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tRAOrder.id !== null) {
                TRAOrder.update(vm.tRAOrder, onSaveSuccess, onSaveError);
            } else {
                TRAOrder.save(vm.tRAOrder, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:tRAOrderUpdate', result);
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
