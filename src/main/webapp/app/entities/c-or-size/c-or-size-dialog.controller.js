(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORSizeDialogController', CORSizeDialogController);

    CORSizeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CORSize', 'CORNetwork'];

    function CORSizeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CORSize, CORNetwork) {
        var vm = this;

        vm.cORSize = entity;
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
            if (vm.cORSize.id !== null) {
                CORSize.update(vm.cORSize, onSaveSuccess, onSaveError);
            } else {
                CORSize.save(vm.cORSize, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:cORSizeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
