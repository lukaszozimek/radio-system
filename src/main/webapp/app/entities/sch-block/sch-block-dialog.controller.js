(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('SchBlockDialogController', SchBlockDialogController);

    SchBlockDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SchBlock', 'SchPlaylist', 'SchTemplate'];

    function SchBlockDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SchBlock, SchPlaylist, SchTemplate) {
        var vm = this;

        vm.schBlock = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.schplaylists = SchPlaylist.query();
        vm.schtemplates = SchTemplate.query();
        vm.schblocks = SchBlock.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.schBlock.id !== null) {
                SchBlock.update(vm.schBlock, onSaveSuccess, onSaveError);
            } else {
                SchBlock.save(vm.schBlock, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:schBlockUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.scheduledStartTime = false;
        vm.datePickerOpenStatus.scheduledEndTime = false;
        vm.datePickerOpenStatus.startTime = false;
        vm.datePickerOpenStatus.endTime = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
