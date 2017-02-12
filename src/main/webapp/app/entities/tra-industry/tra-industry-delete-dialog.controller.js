(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraIndustryDeleteController',TraIndustryDeleteController);

    TraIndustryDeleteController.$inject = ['$uibModalInstance', 'entity', 'TraIndustry'];

    function TraIndustryDeleteController($uibModalInstance, entity, TraIndustry) {
        var vm = this;

        vm.traIndustry = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TraIndustry.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
