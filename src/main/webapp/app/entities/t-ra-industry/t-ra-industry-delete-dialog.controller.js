(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TRAIndustryDeleteController',TRAIndustryDeleteController);

    TRAIndustryDeleteController.$inject = ['$uibModalInstance', 'entity', 'TRAIndustry'];

    function TRAIndustryDeleteController($uibModalInstance, entity, TRAIndustry) {
        var vm = this;

        vm.tRAIndustry = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TRAIndustry.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
