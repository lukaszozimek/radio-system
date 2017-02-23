(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraIndustryDialogController', TraIndustryDialogController);

    TraIndustryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TraIndustry', 'CorNetwork'];

    function TraIndustryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TraIndustry, CorNetwork) {
        var vm = this;

        vm.traIndustry = entity;
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
            if (vm.traIndustry.id !== null) {
                TraIndustry.update(vm.traIndustry, onSaveSuccess, onSaveError);
            } else {
                TraIndustry.save(vm.traIndustry, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:traIndustryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
