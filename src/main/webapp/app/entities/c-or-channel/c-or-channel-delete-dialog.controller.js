(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORChannelDeleteController',CORChannelDeleteController);

    CORChannelDeleteController.$inject = ['$uibModalInstance', 'entity', 'CORChannel'];

    function CORChannelDeleteController($uibModalInstance, entity, CORChannel) {
        var vm = this;

        vm.cORChannel = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CORChannel.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
