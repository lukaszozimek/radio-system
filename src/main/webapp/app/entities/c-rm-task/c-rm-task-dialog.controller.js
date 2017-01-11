(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CRMTaskDialogController', CRMTaskDialogController);

    CRMTaskDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CRMTask', 'CORNetwork'];

    function CRMTaskDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CRMTask, CORNetwork) {
        var vm = this;

        vm.cRMTask = entity;
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
            if (vm.cRMTask.id !== null) {
                CRMTask.update(vm.cRMTask, onSaveSuccess, onSaveError);
            } else {
                CRMTask.save(vm.cRMTask, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:cRMTaskUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.activityDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
