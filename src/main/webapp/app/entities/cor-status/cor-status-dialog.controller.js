(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorStatusDialogController', CorStatusDialogController);

    CorStatusDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CorStatus'];

    function CorStatusDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CorStatus) {
        var vm = this;

        vm.corStatus = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.corStatus.id !== null) {
                CorStatus.update(vm.corStatus, onSaveSuccess, onSaveError);
            } else {
                CorStatus.save(vm.corStatus, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:corStatusUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
