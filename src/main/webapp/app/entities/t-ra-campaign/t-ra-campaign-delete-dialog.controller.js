(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TRACampaignDeleteController',TRACampaignDeleteController);

    TRACampaignDeleteController.$inject = ['$uibModalInstance', 'entity', 'TRACampaign'];

    function TRACampaignDeleteController($uibModalInstance, entity, TRACampaign) {
        var vm = this;

        vm.tRACampaign = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TRACampaign.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
