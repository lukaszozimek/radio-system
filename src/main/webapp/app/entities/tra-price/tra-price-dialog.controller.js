(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraPriceDialogController', TraPriceDialogController);

    TraPriceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'TraPrice', 'CorCurrency', 'CorNetwork'];

    function TraPriceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, TraPrice, CorCurrency, CorNetwork) {
        var vm = this;

        vm.traPrice = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.currencies = CorCurrency.query({filter: 'traprice-is-null'});
        $q.all([vm.traPrice.$promise, vm.currencies.$promise]).then(function() {
            if (!vm.traPrice.currencyId) {
                return $q.reject();
            }
            return CorCurrency.get({id : vm.traPrice.currencyId}).$promise;
        }).then(function(currency) {
            vm.currencies.push(currency);
        });
        vm.cornetworks = CorNetwork.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.traPrice.id !== null) {
                TraPrice.update(vm.traPrice, onSaveSuccess, onSaveError);
            } else {
                TraPrice.save(vm.traPrice, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:traPriceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.validFrom = false;
        vm.datePickerOpenStatus.validTo = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
