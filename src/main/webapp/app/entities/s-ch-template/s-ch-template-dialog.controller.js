(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('SCHTemplateDialogController', SCHTemplateDialogController);

    SCHTemplateDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SCHTemplate', 'CORChannel'];

    function SCHTemplateDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SCHTemplate, CORChannel) {
        var vm = this;

        vm.sCHTemplate = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
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
            if (vm.sCHTemplate.id !== null) {
                SCHTemplate.update(vm.sCHTemplate, onSaveSuccess, onSaveError);
            } else {
                SCHTemplate.save(vm.sCHTemplate, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:sCHTemplateUpdate', result);
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
