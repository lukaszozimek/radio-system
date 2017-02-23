(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibFileItemDialogController', LibFileItemDialogController);

    LibFileItemDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LibFileItem', 'LibLibrary'];

    function LibFileItemDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LibFileItem, LibLibrary) {
        var vm = this;

        vm.libFileItem = entity;
        vm.clear = clear;
        vm.save = save;
        vm.liblibraries = LibLibrary.query();
        vm.libfileitems = LibFileItem.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.libFileItem.id !== null) {
                LibFileItem.update(vm.libFileItem, onSaveSuccess, onSaveError);
            } else {
                LibFileItem.save(vm.libFileItem, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:libFileItemUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
