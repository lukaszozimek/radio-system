(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorAddressDialogController', CorAddressDialogController);

    CorAddressDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CorAddress', 'CorNetwork'];

    function CorAddressDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CorAddress, CorNetwork) {
        var vm = this;

        vm.corAddress = entity;
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
            if (vm.corAddress.id !== null) {
                CorAddress.update(vm.corAddress, onSaveSuccess, onSaveError);
            } else {
                CorAddress.save(vm.corAddress, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:corAddressUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
