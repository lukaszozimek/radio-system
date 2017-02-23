(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CfgMarkerConfigurationDialogController', CfgMarkerConfigurationDialogController);

    CfgMarkerConfigurationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CfgMarkerConfiguration', 'CorNetwork'];

    function CfgMarkerConfigurationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CfgMarkerConfiguration, CorNetwork) {
        var vm = this;

        vm.cfgMarkerConfiguration = entity;
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
            if (vm.cfgMarkerConfiguration.id !== null) {
                CfgMarkerConfiguration.update(vm.cfgMarkerConfiguration, onSaveSuccess, onSaveError);
            } else {
                CfgMarkerConfiguration.save(vm.cfgMarkerConfiguration, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:cfgMarkerConfigurationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
