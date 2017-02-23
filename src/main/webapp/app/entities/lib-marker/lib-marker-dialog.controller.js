(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibMarkerDialogController', LibMarkerDialogController);

    LibMarkerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LibMarker', 'LibMediaItem'];

    function LibMarkerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LibMarker, LibMediaItem) {
        var vm = this;

        vm.libMarker = entity;
        vm.clear = clear;
        vm.save = save;
        vm.libmediaitems = LibMediaItem.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.libMarker.id !== null) {
                LibMarker.update(vm.libMarker, onSaveSuccess, onSaveError);
            } else {
                LibMarker.save(vm.libMarker, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:libMarkerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
