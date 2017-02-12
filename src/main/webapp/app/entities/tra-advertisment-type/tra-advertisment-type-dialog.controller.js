(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraAdvertismentTypeDialogController', TraAdvertismentTypeDialogController);

    TraAdvertismentTypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TraAdvertismentType'];

    function TraAdvertismentTypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TraAdvertismentType) {
        var vm = this;

        vm.traAdvertismentType = entity;
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
            if (vm.traAdvertismentType.id !== null) {
                TraAdvertismentType.update(vm.traAdvertismentType, onSaveSuccess, onSaveError);
            } else {
                TraAdvertismentType.save(vm.traAdvertismentType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:traAdvertismentTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
