(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorNetworkDeleteController',CorNetworkDeleteController);

    CorNetworkDeleteController.$inject = ['$uibModalInstance', 'entity', 'CorNetwork'];

    function CorNetworkDeleteController($uibModalInstance, entity, CorNetwork) {
        var vm = this;

        vm.corNetwork = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CorNetwork.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
