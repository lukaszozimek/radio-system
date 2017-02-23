(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorContactDialogController', CorContactDialogController);

    CorContactDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CorContact', 'CorNetwork', 'CorPerson'];

    function CorContactDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CorContact, CorNetwork, CorPerson) {
        var vm = this;

        vm.corContact = entity;
        vm.clear = clear;
        vm.save = save;
        vm.cornetworks = CorNetwork.query();
        vm.corpeople = CorPerson.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.corContact.id !== null) {
                CorContact.update(vm.corContact, onSaveSuccess, onSaveError);
            } else {
                CorContact.save(vm.corContact, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:corContactUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
