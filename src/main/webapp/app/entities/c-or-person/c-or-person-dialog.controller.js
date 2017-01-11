(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORPersonDialogController', CORPersonDialogController);

    CORPersonDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CORPerson', 'CORNetwork'];

    function CORPersonDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CORPerson, CORNetwork) {
        var vm = this;

        vm.cORPerson = entity;
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
            if (vm.cORPerson.id !== null) {
                CORPerson.update(vm.cORPerson, onSaveSuccess, onSaveError);
            } else {
                CORPerson.save(vm.cORPerson, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:cORPersonUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
