(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorPropertyKeyDeleteController',CorPropertyKeyDeleteController);

    CorPropertyKeyDeleteController.$inject = ['$uibModalInstance', 'entity', 'CorPropertyKey'];

    function CorPropertyKeyDeleteController($uibModalInstance, entity, CorPropertyKey) {
        var vm = this;

        vm.corPropertyKey = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CorPropertyKey.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
