(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORContactDialogController', CORContactDialogController);

    CORContactDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CORContact', 'CORNetwork'];

    function CORContactDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CORContact, CORNetwork) {
        var vm = this;

        vm.cORContact = entity;
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
            if (vm.cORContact.id !== null) {
                CORContact.update(vm.cORContact, onSaveSuccess, onSaveError);
            } else {
                CORContact.save(vm.cORContact, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:cORContactUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
