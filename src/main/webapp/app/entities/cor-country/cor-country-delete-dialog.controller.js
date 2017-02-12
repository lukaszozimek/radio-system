(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorCountryDeleteController',CorCountryDeleteController);

    CorCountryDeleteController.$inject = ['$uibModalInstance', 'entity', 'CorCountry'];

    function CorCountryDeleteController($uibModalInstance, entity, CorCountry) {
        var vm = this;

        vm.corCountry = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CorCountry.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
