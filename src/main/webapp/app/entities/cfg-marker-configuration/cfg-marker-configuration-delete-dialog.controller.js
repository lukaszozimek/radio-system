(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CfgMarkerConfigurationDeleteController',CfgMarkerConfigurationDeleteController);

    CfgMarkerConfigurationDeleteController.$inject = ['$uibModalInstance', 'entity', 'CfgMarkerConfiguration'];

    function CfgMarkerConfigurationDeleteController($uibModalInstance, entity, CfgMarkerConfiguration) {
        var vm = this;

        vm.cfgMarkerConfiguration = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CfgMarkerConfiguration.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
