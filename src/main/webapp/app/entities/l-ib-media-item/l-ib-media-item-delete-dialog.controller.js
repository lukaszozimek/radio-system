(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBMediaItemDeleteController',LIBMediaItemDeleteController);

    LIBMediaItemDeleteController.$inject = ['$uibModalInstance', 'entity', 'LIBMediaItem'];

    function LIBMediaItemDeleteController($uibModalInstance, entity, LIBMediaItem) {
        var vm = this;

        vm.lIBMediaItem = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LIBMediaItem.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
