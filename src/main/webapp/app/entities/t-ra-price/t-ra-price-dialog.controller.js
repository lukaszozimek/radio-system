(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TRAPriceDialogController', TRAPriceDialogController);

    TRAPriceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TRAPrice', 'CORNetwork'];

    function TRAPriceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TRAPrice, CORNetwork) {
        var vm = this;

        vm.tRAPrice = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.cornetworks = CORNetwork.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tRAPrice.id !== null) {
                TRAPrice.update(vm.tRAPrice, onSaveSuccess, onSaveError);
            } else {
                TRAPrice.save(vm.tRAPrice, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:tRAPriceUpdate', result);
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
