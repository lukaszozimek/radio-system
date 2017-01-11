(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TRABlockPriceDeleteController',TRABlockPriceDeleteController);

    TRABlockPriceDeleteController.$inject = ['$uibModalInstance', 'entity', 'TRABlockPrice'];

    function TRABlockPriceDeleteController($uibModalInstance, entity, TRABlockPrice) {
        var vm = this;

        vm.tRABlockPrice = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TRABlockPrice.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
