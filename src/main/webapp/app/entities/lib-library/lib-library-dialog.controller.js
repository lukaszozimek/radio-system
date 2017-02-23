(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibLibraryDialogController', LibLibraryDialogController);

    LibLibraryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LibLibrary', 'CorNetwork'];

    function LibLibraryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LibLibrary, CorNetwork) {
        var vm = this;

        vm.libLibrary = entity;
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
            if (vm.libLibrary.id !== null) {
                LibLibrary.update(vm.libLibrary, onSaveSuccess, onSaveError);
            } else {
                LibLibrary.save(vm.libLibrary, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:libLibraryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
