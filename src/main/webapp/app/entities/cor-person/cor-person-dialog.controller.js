(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorPersonDialogController', CorPersonDialogController);

    CorPersonDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CorPerson', 'CorNetwork', 'CorContact'];

    function CorPersonDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CorPerson, CorNetwork, CorContact) {
        var vm = this;

        vm.corPerson = entity;
        vm.clear = clear;
        vm.save = save;
        vm.cornetworks = CorNetwork.query();
        vm.corcontacts = CorContact.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.corPerson.id !== null) {
                CorPerson.update(vm.corPerson, onSaveSuccess, onSaveError);
            } else {
                CorPerson.save(vm.corPerson, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:corPersonUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
