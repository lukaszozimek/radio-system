(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CRMContactDialogController', CRMContactDialogController);

    CRMContactDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CRMContact', 'CORNetwork'];

    function CRMContactDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CRMContact, CORNetwork) {
        var vm = this;

        vm.cRMContact = entity;
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
            if (vm.cRMContact.id !== null) {
                CRMContact.update(vm.cRMContact, onSaveSuccess, onSaveError);
            } else {
                CRMContact.save(vm.cRMContact, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:cRMContactUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
