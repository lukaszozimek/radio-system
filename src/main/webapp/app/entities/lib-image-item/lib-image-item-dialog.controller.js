(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibImageItemDialogController', LibImageItemDialogController);

    LibImageItemDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LibImageItem', 'LibLibrary'];

    function LibImageItemDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LibImageItem, LibLibrary) {
        var vm = this;

        vm.libImageItem = entity;
        vm.clear = clear;
        vm.save = save;
        vm.liblibraries = LibLibrary.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.libImageItem.id !== null) {
                LibImageItem.update(vm.libImageItem, onSaveSuccess, onSaveError);
            } else {
                LibImageItem.save(vm.libImageItem, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:libImageItemUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
