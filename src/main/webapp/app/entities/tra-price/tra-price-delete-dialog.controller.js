(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraPriceDeleteController',TraPriceDeleteController);

    TraPriceDeleteController.$inject = ['$uibModalInstance', 'entity', 'TraPrice'];

    function TraPriceDeleteController($uibModalInstance, entity, TraPrice) {
        var vm = this;

        vm.traPrice = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TraPrice.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
