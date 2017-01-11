(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('SCHEmissionDialogController', SCHEmissionDialogController);

    SCHEmissionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SCHEmission', 'SCHBlock', 'LIBMediaItem', 'SCHTemplate'];

    function SCHEmissionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SCHEmission, SCHBlock, LIBMediaItem, SCHTemplate) {
        var vm = this;

        vm.sCHEmission = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.schblocks = SCHBlock.query();
        vm.libmediaitems = LIBMediaItem.query();
        vm.schtemplates = SCHTemplate.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.sCHEmission.id !== null) {
                SCHEmission.update(vm.sCHEmission, onSaveSuccess, onSaveError);
            } else {
                SCHEmission.save(vm.sCHEmission, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:sCHEmissionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.startTime = false;
        vm.datePickerOpenStatus.endTime = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
