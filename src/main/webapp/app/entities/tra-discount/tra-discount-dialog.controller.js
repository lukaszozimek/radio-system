(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraDiscountDialogController', TraDiscountDialogController);

    TraDiscountDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TraDiscount', 'CorNetwork'];

    function TraDiscountDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TraDiscount, CorNetwork) {
        var vm = this;

        vm.traDiscount = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
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
            if (vm.traDiscount.id !== null) {
                TraDiscount.update(vm.traDiscount, onSaveSuccess, onSaveError);
            } else {
                TraDiscount.save(vm.traDiscount, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:traDiscountUpdate', result);
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
