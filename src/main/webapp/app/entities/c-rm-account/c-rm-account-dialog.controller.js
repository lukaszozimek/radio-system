(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CRMAccountDialogController', CRMAccountDialogController);

    CRMAccountDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CRMAccount', 'CORNetwork'];

    function CRMAccountDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CRMAccount, CORNetwork) {
        var vm = this;

        vm.cRMAccount = entity;
        vm.clear = clear;
        vm.save = save;
        vm.cornetworks = CORNetwork.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.cRMAccount.id !== null) {
                CRMAccount.update(vm.cRMAccount, onSaveSuccess, onSaveError);
            } else {
                CRMAccount.save(vm.cRMAccount, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:cRMAccountUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
