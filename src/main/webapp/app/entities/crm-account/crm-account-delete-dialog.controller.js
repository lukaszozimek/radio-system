(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmAccountDeleteController',CrmAccountDeleteController);

    CrmAccountDeleteController.$inject = ['$uibModalInstance', 'entity', 'CrmAccount'];

    function CrmAccountDeleteController($uibModalInstance, entity, CrmAccount) {
        var vm = this;

        vm.crmAccount = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CrmAccount.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
