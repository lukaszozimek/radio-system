(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraAdvertisementDeleteController',TraAdvertisementDeleteController);

    TraAdvertisementDeleteController.$inject = ['$uibModalInstance', 'entity', 'TraAdvertisement'];

    function TraAdvertisementDeleteController($uibModalInstance, entity, TraAdvertisement) {
        var vm = this;

        vm.traAdvertisement = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TraAdvertisement.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
