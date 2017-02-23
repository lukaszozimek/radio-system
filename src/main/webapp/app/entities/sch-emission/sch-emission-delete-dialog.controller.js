(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('SchEmissionDeleteController',SchEmissionDeleteController);

    SchEmissionDeleteController.$inject = ['$uibModalInstance', 'entity', 'SchEmission'];

    function SchEmissionDeleteController($uibModalInstance, entity, SchEmission) {
        var vm = this;

        vm.schEmission = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SchEmission.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
