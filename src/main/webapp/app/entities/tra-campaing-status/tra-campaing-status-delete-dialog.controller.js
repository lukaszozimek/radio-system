(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraCampaingStatusDeleteController',TraCampaingStatusDeleteController);

    TraCampaingStatusDeleteController.$inject = ['$uibModalInstance', 'entity', 'TraCampaingStatus'];

    function TraCampaingStatusDeleteController($uibModalInstance, entity, TraCampaingStatus) {
        var vm = this;

        vm.traCampaingStatus = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TraCampaingStatus.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
