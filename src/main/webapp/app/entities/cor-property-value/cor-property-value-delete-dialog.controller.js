(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorPropertyValueDeleteController',CorPropertyValueDeleteController);

    CorPropertyValueDeleteController.$inject = ['$uibModalInstance', 'entity', 'CorPropertyValue'];

    function CorPropertyValueDeleteController($uibModalInstance, entity, CorPropertyValue) {
        var vm = this;

        vm.corPropertyValue = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CorPropertyValue.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
