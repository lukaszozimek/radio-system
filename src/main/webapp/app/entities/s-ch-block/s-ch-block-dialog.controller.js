(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('SCHBlockDialogController', SCHBlockDialogController);

    SCHBlockDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SCHBlock', 'CORChannel', 'SCHTemplate'];

    function SCHBlockDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SCHBlock, CORChannel, SCHTemplate) {
        var vm = this;

        vm.sCHBlock = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.schblocks = SCHBlock.query();
        vm.corchannels = CORChannel.query();
        vm.schtemplates = SCHTemplate.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.sCHBlock.id !== null) {
                SCHBlock.update(vm.sCHBlock, onSaveSuccess, onSaveError);
            } else {
                SCHBlock.save(vm.sCHBlock, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:sCHBlockUpdate', result);
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
