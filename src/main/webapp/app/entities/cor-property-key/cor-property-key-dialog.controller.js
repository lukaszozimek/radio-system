(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorPropertyKeyDialogController', CorPropertyKeyDialogController);

    CorPropertyKeyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CorPropertyKey', 'CorNetwork'];

    function CorPropertyKeyDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CorPropertyKey, CorNetwork) {
        var vm = this;

        vm.corPropertyKey = entity;
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
            if (vm.corPropertyKey.id !== null) {
                CorPropertyKey.update(vm.corPropertyKey, onSaveSuccess, onSaveError);
            } else {
                CorPropertyKey.save(vm.corPropertyKey, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:corPropertyKeyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
