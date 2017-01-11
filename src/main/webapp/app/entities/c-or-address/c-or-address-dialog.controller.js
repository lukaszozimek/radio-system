(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORAddressDialogController', CORAddressDialogController);

    CORAddressDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CORAddress', 'CORNetwork'];

    function CORAddressDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CORAddress, CORNetwork) {
        var vm = this;

        vm.cORAddress = entity;
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
            if (vm.cORAddress.id !== null) {
                CORAddress.update(vm.cORAddress, onSaveSuccess, onSaveError);
            } else {
                CORAddress.save(vm.cORAddress, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:cORAddressUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
