(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORAreaDialogController', CORAreaDialogController);

    CORAreaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CORArea', 'CORNetwork'];

    function CORAreaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CORArea, CORNetwork) {
        var vm = this;

        vm.cORArea = entity;
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
            if (vm.cORArea.id !== null) {
                CORArea.update(vm.cORArea, onSaveSuccess, onSaveError);
            } else {
                CORArea.save(vm.cORArea, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:cORAreaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
