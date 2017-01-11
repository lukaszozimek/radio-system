(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORContactDeleteController',CORContactDeleteController);

    CORContactDeleteController.$inject = ['$uibModalInstance', 'entity', 'CORContact'];

    function CORContactDeleteController($uibModalInstance, entity, CORContact) {
        var vm = this;

        vm.cORContact = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CORContact.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
