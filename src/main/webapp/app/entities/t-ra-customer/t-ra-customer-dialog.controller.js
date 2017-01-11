(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TRACustomerDialogController', TRACustomerDialogController);

    TRACustomerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TRACustomer', 'TRAIndustry', 'CORNetwork', 'User'];

    function TRACustomerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TRACustomer, TRAIndustry, CORNetwork, User) {
        var vm = this;

        vm.tRACustomer = entity;
        vm.clear = clear;
        vm.save = save;
        vm.traindustries = TRAIndustry.query();
        vm.cornetworks = CORNetwork.query();
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tRACustomer.id !== null) {
                TRACustomer.update(vm.tRACustomer, onSaveSuccess, onSaveError);
            } else {
                TRACustomer.save(vm.tRACustomer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:tRACustomerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
