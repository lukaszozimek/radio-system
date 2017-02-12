(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmContactStatusDeleteController',CrmContactStatusDeleteController);

    CrmContactStatusDeleteController.$inject = ['$uibModalInstance', 'entity', 'CrmContactStatus'];

    function CrmContactStatusDeleteController($uibModalInstance, entity, CrmContactStatus) {
        var vm = this;

        vm.crmContactStatus = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CrmContactStatus.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
