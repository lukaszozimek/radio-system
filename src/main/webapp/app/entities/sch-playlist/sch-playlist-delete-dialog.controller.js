(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('SchPlaylistDeleteController',SchPlaylistDeleteController);

    SchPlaylistDeleteController.$inject = ['$uibModalInstance', 'entity', 'SchPlaylist'];

    function SchPlaylistDeleteController($uibModalInstance, entity, SchPlaylist) {
        var vm = this;

        vm.schPlaylist = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SchPlaylist.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
