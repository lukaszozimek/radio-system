(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORPropertyValueDeleteController',CORPropertyValueDeleteController);

    CORPropertyValueDeleteController.$inject = ['$uibModalInstance', 'entity', 'CORPropertyValue'];

    function CORPropertyValueDeleteController($uibModalInstance, entity, CORPropertyValue) {
        var vm = this;

        vm.cORPropertyValue = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CORPropertyValue.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
