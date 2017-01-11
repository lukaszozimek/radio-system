(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORAreaDeleteController',CORAreaDeleteController);

    CORAreaDeleteController.$inject = ['$uibModalInstance', 'entity', 'CORArea'];

    function CORAreaDeleteController($uibModalInstance, entity, CORArea) {
        var vm = this;

        vm.cORArea = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CORArea.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
