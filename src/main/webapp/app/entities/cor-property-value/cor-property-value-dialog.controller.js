(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorPropertyValueDialogController', CorPropertyValueDialogController);

    CorPropertyValueDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CorPropertyValue', 'CorPropertyKey', 'LibMediaItem'];

    function CorPropertyValueDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CorPropertyValue, CorPropertyKey, LibMediaItem) {
        var vm = this;

        vm.corPropertyValue = entity;
        vm.clear = clear;
        vm.save = save;
        vm.corpropertykeys = CorPropertyKey.query();
        vm.libmediaitems = LibMediaItem.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.corPropertyValue.id !== null) {
                CorPropertyValue.update(vm.corPropertyValue, onSaveSuccess, onSaveError);
            } else {
                CorPropertyValue.save(vm.corPropertyValue, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:corPropertyValueUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
