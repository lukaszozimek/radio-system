(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TRABlockPriceDialogController', TRABlockPriceDialogController);

    TRABlockPriceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TRABlockPrice'];

    function TRABlockPriceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TRABlockPrice) {
        var vm = this;

        vm.tRABlockPrice = entity;
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
            if (vm.tRABlockPrice.id !== null) {
                TRABlockPrice.update(vm.tRABlockPrice, onSaveSuccess, onSaveError);
            } else {
                TRABlockPrice.save(vm.tRABlockPrice, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:tRABlockPriceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
