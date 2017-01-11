(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORNetworkDialogController', CORNetworkDialogController);

    CORNetworkDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CORNetwork'];

    function CORNetworkDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CORNetwork) {
        var vm = this;

        vm.cORNetwork = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.cORNetwork.id !== null) {
                CORNetwork.update(vm.cORNetwork, onSaveSuccess, onSaveError);
            } else {
                CORNetwork.save(vm.cORNetwork, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:cORNetworkUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
