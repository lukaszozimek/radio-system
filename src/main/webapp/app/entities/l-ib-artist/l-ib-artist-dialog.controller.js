(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBArtistDialogController', LIBArtistDialogController);

    LIBArtistDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LIBArtist', 'CORNetwork'];

    function LIBArtistDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LIBArtist, CORNetwork) {
        var vm = this;

        vm.lIBArtist = entity;
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
            if (vm.lIBArtist.id !== null) {
                LIBArtist.update(vm.lIBArtist, onSaveSuccess, onSaveError);
            } else {
                LIBArtist.save(vm.lIBArtist, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:lIBArtistUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
