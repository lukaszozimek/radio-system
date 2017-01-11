(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TRAEmissionOrderDialogController', TRAEmissionOrderDialogController);

    TRAEmissionOrderDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TRAEmissionOrder'];

    function TRAEmissionOrderDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TRAEmissionOrder) {
        var vm = this;

        vm.tRAEmissionOrder = entity;
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
            if (vm.tRAEmissionOrder.id !== null) {
                TRAEmissionOrder.update(vm.tRAEmissionOrder, onSaveSuccess, onSaveError);
            } else {
                TRAEmissionOrder.save(vm.tRAEmissionOrder, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:tRAEmissionOrderUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
