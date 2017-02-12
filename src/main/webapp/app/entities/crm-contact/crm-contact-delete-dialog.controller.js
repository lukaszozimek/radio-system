(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmContactDeleteController',CrmContactDeleteController);

    CrmContactDeleteController.$inject = ['$uibModalInstance', 'entity', 'CrmContact'];

    function CrmContactDeleteController($uibModalInstance, entity, CrmContact) {
        var vm = this;

        vm.crmContact = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CrmContact.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
