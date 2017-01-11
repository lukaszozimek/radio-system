(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBLibraryDialogController', LIBLibraryDialogController);

    LIBLibraryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LIBLibrary', 'CORNetwork'];

    function LIBLibraryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LIBLibrary, CORNetwork) {
        var vm = this;

        vm.lIBLibrary = entity;
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
            if (vm.lIBLibrary.id !== null) {
                LIBLibrary.update(vm.lIBLibrary, onSaveSuccess, onSaveError);
            } else {
                LIBLibrary.save(vm.lIBLibrary, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:lIBLibraryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
