(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorTagDeleteController',CorTagDeleteController);

    CorTagDeleteController.$inject = ['$uibModalInstance', 'entity', 'CorTag'];

    function CorTagDeleteController($uibModalInstance, entity, CorTag) {
        var vm = this;

        vm.corTag = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CorTag.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
