(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBMarkerDialogController', LIBMarkerDialogController);

    LIBMarkerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LIBMarker', 'LIBMediaItem'];

    function LIBMarkerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LIBMarker, LIBMediaItem) {
        var vm = this;

        vm.lIBMarker = entity;
        vm.clear = clear;
        vm.save = save;
        vm.libmediaitems = LIBMediaItem.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.lIBMarker.id !== null) {
                LIBMarker.update(vm.lIBMarker, onSaveSuccess, onSaveError);
            } else {
                LIBMarker.save(vm.lIBMarker, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:lIBMarkerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
