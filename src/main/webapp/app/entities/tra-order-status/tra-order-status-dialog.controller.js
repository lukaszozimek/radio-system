(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraOrderStatusDialogController', TraOrderStatusDialogController);

    TraOrderStatusDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TraOrderStatus'];

    function TraOrderStatusDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TraOrderStatus) {
        var vm = this;

        vm.traOrderStatus = entity;
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
            if (vm.traOrderStatus.id !== null) {
                TraOrderStatus.update(vm.traOrderStatus, onSaveSuccess, onSaveError);
            } else {
                TraOrderStatus.save(vm.traOrderStatus, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:traOrderStatusUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
