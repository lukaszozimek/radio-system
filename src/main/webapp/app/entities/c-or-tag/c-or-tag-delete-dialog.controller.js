(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORTagDeleteController',CORTagDeleteController);

    CORTagDeleteController.$inject = ['$uibModalInstance', 'entity', 'CORTag'];

    function CORTagDeleteController($uibModalInstance, entity, CORTag) {
        var vm = this;

        vm.cORTag = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CORTag.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
