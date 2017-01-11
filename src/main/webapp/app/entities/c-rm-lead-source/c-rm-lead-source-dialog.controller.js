(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CRMLeadSourceDialogController', CRMLeadSourceDialogController);

    CRMLeadSourceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CRMLeadSource', 'CORNetwork'];

    function CRMLeadSourceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CRMLeadSource, CORNetwork) {
        var vm = this;

        vm.cRMLeadSource = entity;
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
            if (vm.cRMLeadSource.id !== null) {
                CRMLeadSource.update(vm.cRMLeadSource, onSaveSuccess, onSaveError);
            } else {
                CRMLeadSource.save(vm.cRMLeadSource, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:cRMLeadSourceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
