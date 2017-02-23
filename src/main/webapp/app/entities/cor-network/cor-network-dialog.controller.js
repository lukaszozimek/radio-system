(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorNetworkDialogController', CorNetworkDialogController);

    CorNetworkDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CorNetwork'];

    function CorNetworkDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CorNetwork) {
        var vm = this;

        vm.corNetwork = entity;
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
            if (vm.corNetwork.id !== null) {
                CorNetwork.update(vm.corNetwork, onSaveSuccess, onSaveError);
            } else {
                CorNetwork.save(vm.corNetwork, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:corNetworkUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
