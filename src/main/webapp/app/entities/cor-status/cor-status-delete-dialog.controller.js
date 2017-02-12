(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorStatusDeleteController',CorStatusDeleteController);

    CorStatusDeleteController.$inject = ['$uibModalInstance', 'entity', 'CorStatus'];

    function CorStatusDeleteController($uibModalInstance, entity, CorStatus) {
        var vm = this;

        vm.corStatus = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CorStatus.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
