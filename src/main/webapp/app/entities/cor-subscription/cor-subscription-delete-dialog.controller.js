(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorSubscriptionDeleteController',CorSubscriptionDeleteController);

    CorSubscriptionDeleteController.$inject = ['$uibModalInstance', 'entity', 'CorSubscription'];

    function CorSubscriptionDeleteController($uibModalInstance, entity, CorSubscription) {
        var vm = this;

        vm.corSubscription = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CorSubscription.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
