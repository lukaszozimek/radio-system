(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBFileItemDialogController', LIBFileItemDialogController);

    LIBFileItemDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LIBFileItem', 'LIBLibrary'];

    function LIBFileItemDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LIBFileItem, LIBLibrary) {
        var vm = this;

        vm.lIBFileItem = entity;
        vm.clear = clear;
        vm.save = save;
        vm.liblibraries = LIBLibrary.query();
        vm.libfileitems = LIBFileItem.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.lIBFileItem.id !== null) {
                LIBFileItem.update(vm.lIBFileItem, onSaveSuccess, onSaveError);
            } else {
                LIBFileItem.save(vm.lIBFileItem, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:lIBFileItemUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
