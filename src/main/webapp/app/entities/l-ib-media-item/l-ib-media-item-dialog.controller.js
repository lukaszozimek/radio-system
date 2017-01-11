(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBMediaItemDialogController', LIBMediaItemDialogController);

    LIBMediaItemDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LIBMediaItem', 'LIBLibrary', 'LIBLabel', 'LIBArtist', 'LIBAlbum', 'LIBTrack'];

    function LIBMediaItemDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LIBMediaItem, LIBLibrary, LIBLabel, LIBArtist, LIBAlbum, LIBTrack) {
        var vm = this;

        vm.lIBMediaItem = entity;
        vm.clear = clear;
        vm.save = save;
        vm.liblibraries = LIBLibrary.query();
        vm.liblabels = LIBLabel.query();
        vm.libartists = LIBArtist.query();
        vm.libalbums = LIBAlbum.query();
        vm.libtracks = LIBTrack.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.lIBMediaItem.id !== null) {
                LIBMediaItem.update(vm.lIBMediaItem, onSaveSuccess, onSaveError);
            } else {
                LIBMediaItem.save(vm.lIBMediaItem, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:lIBMediaItemUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
