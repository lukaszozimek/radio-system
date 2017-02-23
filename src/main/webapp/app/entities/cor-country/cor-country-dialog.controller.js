(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorCountryDialogController', CorCountryDialogController);

    CorCountryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'CorCountry', 'CorTax', 'CorCurrency', 'CorNetwork'];

    function CorCountryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, CorCountry, CorTax, CorCurrency, CorNetwork) {
        var vm = this;

        vm.corCountry = entity;
        vm.clear = clear;
        vm.save = save;
        vm.taxes = CorTax.query({filter: 'corcountry-is-null'});
        $q.all([vm.corCountry.$promise, vm.taxes.$promise]).then(function() {
            if (!vm.corCountry.taxId) {
                return $q.reject();
            }
            return CorTax.get({id : vm.corCountry.taxId}).$promise;
        }).then(function(tax) {
            vm.taxes.push(tax);
        });
        vm.currnecies = CorCurrency.query({filter: 'corcountry-is-null'});
        $q.all([vm.corCountry.$promise, vm.currnecies.$promise]).then(function() {
            if (!vm.corCountry.currnecyId) {
                return $q.reject();
            }
            return CorCurrency.get({id : vm.corCountry.currnecyId}).$promise;
        }).then(function(currnecy) {
            vm.currnecies.push(currnecy);
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
            if (vm.corCountry.id !== null) {
                CorCountry.update(vm.corCountry, onSaveSuccess, onSaveError);
            } else {
                CorCountry.save(vm.corCountry, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:corCountryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
