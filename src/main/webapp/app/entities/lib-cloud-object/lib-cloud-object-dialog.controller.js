(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibCloudObjectDialogController', LibCloudObjectDialogController);

    LibCloudObjectDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LibCloudObject', 'CorNetwork', 'CorUser'];

    function LibCloudObjectDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LibCloudObject, CorNetwork, CorUser) {
        var vm = this;

        vm.libCloudObject = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.cornetworks = CorNetwork.query();
        vm.corusers = CorUser.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.libCloudObject.id !== null) {
                LibCloudObject.update(vm.libCloudObject, onSaveSuccess, onSaveError);
            } else {
                LibCloudObject.save(vm.libCloudObject, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:libCloudObjectUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
