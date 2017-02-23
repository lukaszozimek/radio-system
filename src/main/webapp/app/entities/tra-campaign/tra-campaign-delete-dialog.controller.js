(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraCampaignDeleteController',TraCampaignDeleteController);

    TraCampaignDeleteController.$inject = ['$uibModalInstance', 'entity', 'TraCampaign'];

    function TraCampaignDeleteController($uibModalInstance, entity, TraCampaign) {
        var vm = this;

        vm.traCampaign = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TraCampaign.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
