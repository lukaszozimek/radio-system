(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORNetworkDeleteController',CORNetworkDeleteController);

    CORNetworkDeleteController.$inject = ['$uibModalInstance', 'entity', 'CORNetwork'];

    function CORNetworkDeleteController($uibModalInstance, entity, CORNetwork) {
        var vm = this;

        vm.cORNetwork = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CORNetwork.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
