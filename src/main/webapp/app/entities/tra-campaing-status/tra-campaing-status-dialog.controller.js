(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraCampaingStatusDialogController', TraCampaingStatusDialogController);

    TraCampaingStatusDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TraCampaingStatus'];

    function TraCampaingStatusDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TraCampaingStatus) {
        var vm = this;

        vm.traCampaingStatus = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.traCampaingStatus.id !== null) {
                TraCampaingStatus.update(vm.traCampaingStatus, onSaveSuccess, onSaveError);
            } else {
                TraCampaingStatus.save(vm.traCampaingStatus, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:traCampaingStatusUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
