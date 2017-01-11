(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CFGMarkerConfigurationDialogController', CFGMarkerConfigurationDialogController);

    CFGMarkerConfigurationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CFGMarkerConfiguration', 'CORNetwork'];

    function CFGMarkerConfigurationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CFGMarkerConfiguration, CORNetwork) {
        var vm = this;

        vm.cFGMarkerConfiguration = entity;
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
            if (vm.cFGMarkerConfiguration.id !== null) {
                CFGMarkerConfiguration.update(vm.cFGMarkerConfiguration, onSaveSuccess, onSaveError);
            } else {
                CFGMarkerConfiguration.save(vm.cFGMarkerConfiguration, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:cFGMarkerConfigurationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
