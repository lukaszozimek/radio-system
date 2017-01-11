(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CRMLeadDialogController', CRMLeadDialogController);

    CRMLeadDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CRMLead', 'CORNetwork'];

    function CRMLeadDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CRMLead, CORNetwork) {
        var vm = this;

        vm.cRMLead = entity;
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
            if (vm.cRMLead.id !== null) {
                CRMLead.update(vm.cRMLead, onSaveSuccess, onSaveError);
            } else {
                CRMLead.save(vm.cRMLead, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:cRMLeadUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
