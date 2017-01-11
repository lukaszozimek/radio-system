(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBImageItemDialogController', LIBImageItemDialogController);

    LIBImageItemDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LIBImageItem', 'LIBLibrary'];

    function LIBImageItemDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LIBImageItem, LIBLibrary) {
        var vm = this;

        vm.lIBImageItem = entity;
        vm.clear = clear;
        vm.save = save;
        vm.liblibraries = LIBLibrary.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.lIBImageItem.id !== null) {
                LIBImageItem.update(vm.lIBImageItem, onSaveSuccess, onSaveError);
            } else {
                LIBImageItem.save(vm.lIBImageItem, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:lIBImageItemUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
