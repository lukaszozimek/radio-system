(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORChannelDialogController', CORChannelDialogController);

    CORChannelDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CORChannel', 'CORNetwork'];

    function CORChannelDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CORChannel, CORNetwork) {
        var vm = this;

        vm.cORChannel = entity;
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
            if (vm.cORChannel.id !== null) {
                CORChannel.update(vm.cORChannel, onSaveSuccess, onSaveError);
            } else {
                CORChannel.save(vm.cORChannel, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:cORChannelUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
