(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmTaskStatusDialogController', CrmTaskStatusDialogController);

    CrmTaskStatusDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CrmTaskStatus', 'CorNetwork'];

    function CrmTaskStatusDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CrmTaskStatus, CorNetwork) {
        var vm = this;

        vm.crmTaskStatus = entity;
        vm.clear = clear;
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
            if (vm.crmTaskStatus.id !== null) {
                CrmTaskStatus.update(vm.crmTaskStatus, onSaveSuccess, onSaveError);
            } else {
                CrmTaskStatus.save(vm.crmTaskStatus, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:crmTaskStatusUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
