(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TRADiscountDialogController', TRADiscountDialogController);

    TRADiscountDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TRADiscount', 'TRACustomer'];

    function TRADiscountDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TRADiscount, TRACustomer) {
        var vm = this;

        vm.tRADiscount = entity;
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
            if (vm.tRADiscount.id !== null) {
                TRADiscount.update(vm.tRADiscount, onSaveSuccess, onSaveError);
            } else {
                TRADiscount.save(vm.tRADiscount, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:tRADiscountUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.validFrom = false;
        vm.datePickerOpenStatus.validTo = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
