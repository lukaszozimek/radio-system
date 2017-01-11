(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORPropertyValueDialogController', CORPropertyValueDialogController);

    CORPropertyValueDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CORPropertyValue', 'CORPropertyKey'];

    function CORPropertyValueDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CORPropertyValue, CORPropertyKey) {
        var vm = this;

        vm.cORPropertyValue = entity;
        vm.clear = clear;
        vm.save = save;
        vm.corpropertykeys = CORPropertyKey.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.cORPropertyValue.id !== null) {
                CORPropertyValue.update(vm.cORPropertyValue, onSaveSuccess, onSaveError);
            } else {
                CORPropertyValue.save(vm.cORPropertyValue, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:cORPropertyValueUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
