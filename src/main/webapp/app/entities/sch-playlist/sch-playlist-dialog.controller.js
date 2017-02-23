(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('SchPlaylistDialogController', SchPlaylistDialogController);

    SchPlaylistDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SchPlaylist', 'CorChannel'];

    function SchPlaylistDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SchPlaylist, CorChannel) {
        var vm = this;

        vm.schPlaylist = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.corchannels = CorChannel.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.schPlaylist.id !== null) {
                SchPlaylist.update(vm.schPlaylist, onSaveSuccess, onSaveError);
            } else {
                SchPlaylist.save(vm.schPlaylist, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:schPlaylistUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.date = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
