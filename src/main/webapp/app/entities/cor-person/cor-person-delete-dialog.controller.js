(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorPersonDeleteController',CorPersonDeleteController);

    CorPersonDeleteController.$inject = ['$uibModalInstance', 'entity', 'CorPerson'];

    function CorPersonDeleteController($uibModalInstance, entity, CorPerson) {
        var vm = this;

        vm.corPerson = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CorPerson.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
