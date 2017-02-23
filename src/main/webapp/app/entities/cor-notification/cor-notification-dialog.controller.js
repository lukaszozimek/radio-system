(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorNotificationDialogController', CorNotificationDialogController);

    CorNotificationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CorNotification', 'CorUser'];

    function CorNotificationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CorNotification, CorUser) {
        var vm = this;

        vm.corNotification = entity;
        vm.clear = clear;
        vm.save = save;
        vm.corusers = CorUser.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.corNotification.id !== null) {
                CorNotification.update(vm.corNotification, onSaveSuccess, onSaveError);
            } else {
                CorNotification.save(vm.corNotification, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:corNotificationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
