(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CRMTaskStatusDialogController', CRMTaskStatusDialogController);

    CRMTaskStatusDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CRMTaskStatus', 'CORNetwork'];

    function CRMTaskStatusDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CRMTaskStatus, CORNetwork) {
        var vm = this;

        vm.cRMTaskStatus = entity;
        vm.clear = clear;
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
            if (vm.cRMTaskStatus.id !== null) {
                CRMTaskStatus.update(vm.cRMTaskStatus, onSaveSuccess, onSaveError);
            } else {
                CRMTaskStatus.save(vm.cRMTaskStatus, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:cRMTaskStatusUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
