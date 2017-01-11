(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TRAAdvertisementDeleteController',TRAAdvertisementDeleteController);

    TRAAdvertisementDeleteController.$inject = ['$uibModalInstance', 'entity', 'TRAAdvertisement'];

    function TRAAdvertisementDeleteController($uibModalInstance, entity, TRAAdvertisement) {
        var vm = this;

        vm.tRAAdvertisement = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TRAAdvertisement.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
