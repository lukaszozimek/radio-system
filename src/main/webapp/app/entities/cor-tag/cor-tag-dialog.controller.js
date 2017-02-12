(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorTagDialogController', CorTagDialogController);

    CorTagDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CorTag', 'CorNetwork', 'LibMediaItem'];

    function CorTagDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CorTag, CorNetwork, LibMediaItem) {
        var vm = this;

        vm.corTag = entity;
        vm.clear = clear;
        vm.save = save;
        vm.cornetworks = CorNetwork.query();
        vm.libmediaitems = LibMediaItem.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.corTag.id !== null) {
                CorTag.update(vm.corTag, onSaveSuccess, onSaveError);
            } else {
                CorTag.save(vm.corTag, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:corTagUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
