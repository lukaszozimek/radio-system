(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorRangeDeleteController',CorRangeDeleteController);

    CorRangeDeleteController.$inject = ['$uibModalInstance', 'entity', 'CorRange'];

    function CorRangeDeleteController($uibModalInstance, entity, CorRange) {
        var vm = this;

        vm.corRange = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CorRange.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
