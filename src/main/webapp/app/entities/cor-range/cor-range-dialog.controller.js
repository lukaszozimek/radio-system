(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorRangeDialogController', CorRangeDialogController);

    CorRangeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CorRange', 'CorNetwork'];

    function CorRangeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CorRange, CorNetwork) {
        var vm = this;

        vm.corRange = entity;
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
            if (vm.corRange.id !== null) {
                CorRange.update(vm.corRange, onSaveSuccess, onSaveError);
            } else {
                CorRange.save(vm.corRange, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:corRangeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
