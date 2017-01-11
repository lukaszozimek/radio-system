(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORPersonDeleteController',CORPersonDeleteController);

    CORPersonDeleteController.$inject = ['$uibModalInstance', 'entity', 'CORPerson'];

    function CORPersonDeleteController($uibModalInstance, entity, CORPerson) {
        var vm = this;

        vm.cORPerson = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CORPerson.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
