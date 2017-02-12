(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorUserDialogController', CorUserDialogController);

    CorUserDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CorUser', 'CorNetwork', 'CorChannel'];

    function CorUserDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CorUser, CorNetwork, CorChannel) {
        var vm = this;

        vm.corUser = entity;
        vm.clear = clear;
        vm.save = save;
        vm.cornetworks = CorNetwork.query();
        vm.corchannels = CorChannel.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.corUser.id !== null) {
                CorUser.update(vm.corUser, onSaveSuccess, onSaveError);
            } else {
                CorUser.save(vm.corUser, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:corUserUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
