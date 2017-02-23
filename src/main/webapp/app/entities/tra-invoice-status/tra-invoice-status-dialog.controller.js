(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraInvoiceStatusDialogController', TraInvoiceStatusDialogController);

    TraInvoiceStatusDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TraInvoiceStatus'];

    function TraInvoiceStatusDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TraInvoiceStatus) {
        var vm = this;

        vm.traInvoiceStatus = entity;
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
            if (vm.traInvoiceStatus.id !== null) {
                TraInvoiceStatus.update(vm.traInvoiceStatus, onSaveSuccess, onSaveError);
            } else {
                TraInvoiceStatus.save(vm.traInvoiceStatus, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:traInvoiceStatusUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
