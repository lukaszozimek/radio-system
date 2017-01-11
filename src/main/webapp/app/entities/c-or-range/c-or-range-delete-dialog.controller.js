(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORRangeDeleteController',CORRangeDeleteController);

    CORRangeDeleteController.$inject = ['$uibModalInstance', 'entity', 'CORRange'];

    function CORRangeDeleteController($uibModalInstance, entity, CORRange) {
        var vm = this;

        vm.cORRange = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CORRange.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
