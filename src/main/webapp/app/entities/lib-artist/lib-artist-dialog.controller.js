(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibArtistDialogController', LibArtistDialogController);

    LibArtistDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LibArtist', 'CorNetwork'];

    function LibArtistDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LibArtist, CorNetwork) {
        var vm = this;

        vm.libArtist = entity;
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
            if (vm.libArtist.id !== null) {
                LibArtist.update(vm.libArtist, onSaveSuccess, onSaveError);
            } else {
                LibArtist.save(vm.libArtist, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:libArtistUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
