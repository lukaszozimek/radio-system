(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraAdvertismentTypeDeleteController',TraAdvertismentTypeDeleteController);

    TraAdvertismentTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'TraAdvertismentType'];

    function TraAdvertismentTypeDeleteController($uibModalInstance, entity, TraAdvertismentType) {
        var vm = this;

        vm.traAdvertismentType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TraAdvertismentType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
