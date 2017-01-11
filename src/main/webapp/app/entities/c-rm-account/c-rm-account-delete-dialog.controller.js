(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CRMAccountDeleteController',CRMAccountDeleteController);

    CRMAccountDeleteController.$inject = ['$uibModalInstance', 'entity', 'CRMAccount'];

    function CRMAccountDeleteController($uibModalInstance, entity, CRMAccount) {
        var vm = this;

        vm.cRMAccount = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CRMAccount.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
