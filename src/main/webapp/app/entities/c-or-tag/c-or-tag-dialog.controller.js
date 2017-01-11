(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORTagDialogController', CORTagDialogController);

    CORTagDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CORTag', 'CORNetwork'];

    function CORTagDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CORTag, CORNetwork) {
        var vm = this;

        vm.cORTag = entity;
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
            if (vm.cORTag.id !== null) {
                CORTag.update(vm.cORTag, onSaveSuccess, onSaveError);
            } else {
                CORTag.save(vm.cORTag, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:cORTagUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
