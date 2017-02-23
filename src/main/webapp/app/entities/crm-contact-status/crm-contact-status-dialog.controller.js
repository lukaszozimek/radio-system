(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmContactStatusDialogController', CrmContactStatusDialogController);

    CrmContactStatusDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CrmContactStatus', 'CorNetwork'];

    function CrmContactStatusDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CrmContactStatus, CorNetwork) {
        var vm = this;

        vm.crmContactStatus = entity;
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
            if (vm.crmContactStatus.id !== null) {
                CrmContactStatus.update(vm.crmContactStatus, onSaveSuccess, onSaveError);
            } else {
                CrmContactStatus.save(vm.crmContactStatus, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:crmContactStatusUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
