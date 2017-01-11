(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CRMLeadStatusDialogController', CRMLeadStatusDialogController);

    CRMLeadStatusDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CRMLeadStatus', 'CORNetwork'];

    function CRMLeadStatusDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CRMLeadStatus, CORNetwork) {
        var vm = this;

        vm.cRMLeadStatus = entity;
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
            if (vm.cRMLeadStatus.id !== null) {
                CRMLeadStatus.update(vm.cRMLeadStatus, onSaveSuccess, onSaveError);
            } else {
                CRMLeadStatus.save(vm.cRMLeadStatus, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:cRMLeadStatusUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
