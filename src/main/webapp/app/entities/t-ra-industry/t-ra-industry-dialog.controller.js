(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TRAIndustryDialogController', TRAIndustryDialogController);

    TRAIndustryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TRAIndustry', 'CORNetwork'];

    function TRAIndustryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TRAIndustry, CORNetwork) {
        var vm = this;

        vm.tRAIndustry = entity;
        vm.clear = clear;
        vm.save = save;
        vm.cornetworks = CORNetwork.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tRAIndustry.id !== null) {
                TRAIndustry.update(vm.tRAIndustry, onSaveSuccess, onSaveError);
            } else {
                TRAIndustry.save(vm.tRAIndustry, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:tRAIndustryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
