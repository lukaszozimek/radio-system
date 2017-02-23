(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibLabelDialogController', LibLabelDialogController);

    LibLabelDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LibLabel', 'CorNetwork'];

    function LibLabelDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LibLabel, CorNetwork) {
        var vm = this;

        vm.libLabel = entity;
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
            if (vm.libLabel.id !== null) {
                LibLabel.update(vm.libLabel, onSaveSuccess, onSaveError);
            } else {
                LibLabel.save(vm.libLabel, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:libLabelUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
