(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorChannelDeleteController',CorChannelDeleteController);

    CorChannelDeleteController.$inject = ['$uibModalInstance', 'entity', 'CorChannel'];

    function CorChannelDeleteController($uibModalInstance, entity, CorChannel) {
        var vm = this;

        vm.corChannel = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CorChannel.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
