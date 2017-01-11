(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBCloudObjectDialogController', LIBCloudObjectDialogController);

    LIBCloudObjectDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LIBCloudObject', 'CORNetwork', 'User'];

    function LIBCloudObjectDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LIBCloudObject, CORNetwork, User) {
        var vm = this;

        vm.lIBCloudObject = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.cornetworks = CORNetwork.query();
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.lIBCloudObject.id !== null) {
                LIBCloudObject.update(vm.lIBCloudObject, onSaveSuccess, onSaveError);
            } else {
                LIBCloudObject.save(vm.lIBCloudObject, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:lIBCloudObjectUpdate', result);
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
