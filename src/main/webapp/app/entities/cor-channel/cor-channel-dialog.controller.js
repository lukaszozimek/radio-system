(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorChannelDialogController', CorChannelDialogController);

    CorChannelDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CorChannel', 'CorNetwork'];

    function CorChannelDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CorChannel, CorNetwork) {
        var vm = this;

        vm.corChannel = entity;
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
            if (vm.corChannel.id !== null) {
                CorChannel.update(vm.corChannel, onSaveSuccess, onSaveError);
            } else {
                CorChannel.save(vm.corChannel, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:corChannelUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
