(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORPropertyKeyDialogController', CORPropertyKeyDialogController);

    CORPropertyKeyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CORPropertyKey', 'CORNetwork'];

    function CORPropertyKeyDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CORPropertyKey, CORNetwork) {
        var vm = this;

        vm.cORPropertyKey = entity;
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
            if (vm.cORPropertyKey.id !== null) {
                CORPropertyKey.update(vm.cORPropertyKey, onSaveSuccess, onSaveError);
            } else {
                CORPropertyKey.save(vm.cORPropertyKey, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:cORPropertyKeyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
