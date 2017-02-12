(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorNotificationDeleteController',CorNotificationDeleteController);

    CorNotificationDeleteController.$inject = ['$uibModalInstance', 'entity', 'CorNotification'];

    function CorNotificationDeleteController($uibModalInstance, entity, CorNotification) {
        var vm = this;

        vm.corNotification = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CorNotification.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
