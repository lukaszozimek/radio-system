(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('SchEmissionDialogController', SchEmissionDialogController);

    SchEmissionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SchEmission', 'LibMediaItem', 'SchBlock', 'TraCampaign'];

    function SchEmissionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SchEmission, LibMediaItem, SchBlock, TraCampaign) {
        var vm = this;

        vm.schEmission = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.libmediaitems = LibMediaItem.query();
        vm.schblocks = SchBlock.query();
        vm.tracampaigns = TraCampaign.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.schEmission.id !== null) {
                SchEmission.update(vm.schEmission, onSaveSuccess, onSaveError);
            } else {
                SchEmission.save(vm.schEmission, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:schEmissionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.startTime = false;
        vm.datePickerOpenStatus.endTime = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
