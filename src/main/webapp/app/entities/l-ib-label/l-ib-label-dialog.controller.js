(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBLabelDialogController', LIBLabelDialogController);

    LIBLabelDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LIBLabel', 'CORNetwork'];

    function LIBLabelDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LIBLabel, CORNetwork) {
        var vm = this;

        vm.lIBLabel = entity;
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
            if (vm.lIBLabel.id !== null) {
                LIBLabel.update(vm.lIBLabel, onSaveSuccess, onSaveError);
            } else {
                LIBLabel.save(vm.lIBLabel, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:lIBLabelUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
