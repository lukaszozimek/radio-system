(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORPropertyKeyDeleteController',CORPropertyKeyDeleteController);

    CORPropertyKeyDeleteController.$inject = ['$uibModalInstance', 'entity', 'CORPropertyKey'];

    function CORPropertyKeyDeleteController($uibModalInstance, entity, CORPropertyKey) {
        var vm = this;

        vm.cORPropertyKey = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CORPropertyKey.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
