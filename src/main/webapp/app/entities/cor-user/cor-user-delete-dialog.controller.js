(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorUserDeleteController',CorUserDeleteController);

    CorUserDeleteController.$inject = ['$uibModalInstance', 'entity', 'CorUser'];

    function CorUserDeleteController($uibModalInstance, entity, CorUser) {
        var vm = this;

        vm.corUser = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CorUser.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
