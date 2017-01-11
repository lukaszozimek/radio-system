(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORSizeDeleteController',CORSizeDeleteController);

    CORSizeDeleteController.$inject = ['$uibModalInstance', 'entity', 'CORSize'];

    function CORSizeDeleteController($uibModalInstance, entity, CORSize) {
        var vm = this;

        vm.cORSize = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CORSize.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
