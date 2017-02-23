(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorCurrencyDeleteController',CorCurrencyDeleteController);

    CorCurrencyDeleteController.$inject = ['$uibModalInstance', 'entity', 'CorCurrency'];

    function CorCurrencyDeleteController($uibModalInstance, entity, CorCurrency) {
        var vm = this;

        vm.corCurrency = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CorCurrency.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
