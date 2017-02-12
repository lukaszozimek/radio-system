(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorAreaDialogController', CorAreaDialogController);

    CorAreaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CorArea', 'CorNetwork'];

    function CorAreaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CorArea, CorNetwork) {
        var vm = this;

        vm.corArea = entity;
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
            if (vm.corArea.id !== null) {
                CorArea.update(vm.corArea, onSaveSuccess, onSaveError);
            } else {
                CorArea.save(vm.corArea, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:corAreaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
