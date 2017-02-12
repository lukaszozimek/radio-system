(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorAddressDeleteController',CorAddressDeleteController);

    CorAddressDeleteController.$inject = ['$uibModalInstance', 'entity', 'CorAddress'];

    function CorAddressDeleteController($uibModalInstance, entity, CorAddress) {
        var vm = this;

        vm.corAddress = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CorAddress.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
