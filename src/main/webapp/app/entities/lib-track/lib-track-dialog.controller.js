(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibTrackDialogController', LibTrackDialogController);

    LibTrackDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LibTrack', 'LibAlbum', 'LibArtist', 'CorNetwork'];

    function LibTrackDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LibTrack, LibAlbum, LibArtist, CorNetwork) {
        var vm = this;

        vm.libTrack = entity;
        vm.clear = clear;
        vm.save = save;
        vm.libalbums = LibAlbum.query();
        vm.libartists = LibArtist.query();
        vm.cornetworks = CorNetwork.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.libTrack.id !== null) {
                LibTrack.update(vm.libTrack, onSaveSuccess, onSaveError);
            } else {
                LibTrack.save(vm.libTrack, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:libTrackUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
