(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBArtistDeleteController',LIBArtistDeleteController);

    LIBArtistDeleteController.$inject = ['$uibModalInstance', 'entity', 'LIBArtist'];

    function LIBArtistDeleteController($uibModalInstance, entity, LIBArtist) {
        var vm = this;

        vm.lIBArtist = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LIBArtist.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
