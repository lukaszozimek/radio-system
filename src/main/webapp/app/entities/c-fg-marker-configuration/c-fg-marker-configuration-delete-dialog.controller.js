(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CFGMarkerConfigurationDeleteController',CFGMarkerConfigurationDeleteController);

    CFGMarkerConfigurationDeleteController.$inject = ['$uibModalInstance', 'entity', 'CFGMarkerConfiguration'];

    function CFGMarkerConfigurationDeleteController($uibModalInstance, entity, CFGMarkerConfiguration) {
        var vm = this;

        vm.cFGMarkerConfiguration = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CFGMarkerConfiguration.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
