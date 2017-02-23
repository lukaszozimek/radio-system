(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorSizeDialogController', CorSizeDialogController);

    CorSizeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CorSize', 'CorNetwork'];

    function CorSizeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CorSize, CorNetwork) {
        var vm = this;

        vm.corSize = entity;
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
            if (vm.corSize.id !== null) {
                CorSize.update(vm.corSize, onSaveSuccess, onSaveError);
            } else {
                CorSize.save(vm.corSize, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:corSizeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
