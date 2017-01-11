(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TRAInvoiceDialogController', TRAInvoiceDialogController);

    TRAInvoiceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'TRAInvoice', 'TRAOrder'];

    function TRAInvoiceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, TRAInvoice, TRAOrder) {
        var vm = this;

        vm.tRAInvoice = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.orders = TRAOrder.query({filter: 'trainvoice-is-null'});
        $q.all([vm.tRAInvoice.$promise, vm.orders.$promise]).then(function() {
            if (!vm.tRAInvoice.orderId) {
                return $q.reject();
            }
            return TRAOrder.get({id : vm.tRAInvoice.orderId}).$promise;
        }).then(function(order) {
            vm.orders.push(order);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tRAInvoice.id !== null) {
                TRAInvoice.update(vm.tRAInvoice, onSaveSuccess, onSaveError);
            } else {
                TRAInvoice.save(vm.tRAInvoice, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:tRAInvoiceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.paymentDay = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
