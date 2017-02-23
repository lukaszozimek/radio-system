(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorSizeDeleteController',CorSizeDeleteController);

    CorSizeDeleteController.$inject = ['$uibModalInstance', 'entity', 'CorSize'];

    function CorSizeDeleteController($uibModalInstance, entity, CorSize) {
        var vm = this;

        vm.corSize = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CorSize.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
