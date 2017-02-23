(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibMediaItemDialogController', LibMediaItemDialogController);

    LibMediaItemDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LibMediaItem', 'LibLibrary', 'LibLabel', 'LibArtist', 'LibAlbum', 'LibTrack'];

    function LibMediaItemDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LibMediaItem, LibLibrary, LibLabel, LibArtist, LibAlbum, LibTrack) {
        var vm = this;

        vm.libMediaItem = entity;
        vm.clear = clear;
        vm.save = save;
        vm.liblibraries = LibLibrary.query();
        vm.liblabels = LibLabel.query();
        vm.libartists = LibArtist.query();
        vm.libalbums = LibAlbum.query();
        vm.libtracks = LibTrack.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.libMediaItem.id !== null) {
                LibMediaItem.update(vm.libMediaItem, onSaveSuccess, onSaveError);
            } else {
                LibMediaItem.save(vm.libMediaItem, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:libMediaItemUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
