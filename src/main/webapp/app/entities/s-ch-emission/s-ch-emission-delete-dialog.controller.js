(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('SCHEmissionDeleteController',SCHEmissionDeleteController);

    SCHEmissionDeleteController.$inject = ['$uibModalInstance', 'entity', 'SCHEmission'];

    function SCHEmissionDeleteController($uibModalInstance, entity, SCHEmission) {
        var vm = this;

        vm.sCHEmission = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SCHEmission.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
