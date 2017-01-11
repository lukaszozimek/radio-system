(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORAddressDeleteController',CORAddressDeleteController);

    CORAddressDeleteController.$inject = ['$uibModalInstance', 'entity', 'CORAddress'];

    function CORAddressDeleteController($uibModalInstance, entity, CORAddress) {
        var vm = this;

        vm.cORAddress = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CORAddress.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
