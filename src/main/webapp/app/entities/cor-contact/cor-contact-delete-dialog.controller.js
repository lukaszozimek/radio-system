(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorContactDeleteController',CorContactDeleteController);

    CorContactDeleteController.$inject = ['$uibModalInstance', 'entity', 'CorContact'];

    function CorContactDeleteController($uibModalInstance, entity, CorContact) {
        var vm = this;

        vm.corContact = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CorContact.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
