(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorTaxDialogController', CorTaxDialogController);

    CorTaxDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CorTax', 'CorNetwork'];

    function CorTaxDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CorTax, CorNetwork) {
        var vm = this;

        vm.corTax = entity;
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
            if (vm.corTax.id !== null) {
                CorTax.update(vm.corTax, onSaveSuccess, onSaveError);
            } else {
                CorTax.save(vm.corTax, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:corTaxUpdate', result);
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
