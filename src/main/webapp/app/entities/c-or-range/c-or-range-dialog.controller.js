(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORRangeDialogController', CORRangeDialogController);

    CORRangeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CORRange', 'CORNetwork'];

    function CORRangeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CORRange, CORNetwork) {
        var vm = this;

        vm.cORRange = entity;
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
            if (vm.cORRange.id !== null) {
                CORRange.update(vm.cORRange, onSaveSuccess, onSaveError);
            } else {
                CORRange.save(vm.cORRange, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:cORRangeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
