(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBTrackDialogController', LIBTrackDialogController);

    LIBTrackDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LIBTrack', 'LIBAlbum', 'LIBArtist', 'CORNetwork'];

    function LIBTrackDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LIBTrack, LIBAlbum, LIBArtist, CORNetwork) {
        var vm = this;

        vm.lIBTrack = entity;
        vm.clear = clear;
        vm.save = save;
        vm.libalbums = LIBAlbum.query();
        vm.libartists = LIBArtist.query();
        vm.cornetworks = CORNetwork.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.lIBTrack.id !== null) {
                LIBTrack.update(vm.lIBTrack, onSaveSuccess, onSaveError);
            } else {
                LIBTrack.save(vm.lIBTrack, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:lIBTrackUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
