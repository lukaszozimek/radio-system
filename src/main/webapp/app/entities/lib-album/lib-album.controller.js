(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibAlbumController', LibAlbumController);

    LibAlbumController.$inject = ['$scope', '$state', 'LibAlbum'];

    function LibAlbumController ($scope, $state, LibAlbum) {
        var vm = this;

        vm.libAlbums = [];

        loadAll();

        function loadAll() {
            LibAlbum.query(function(result) {
                vm.libAlbums = result;
                vm.searchQuery = null;
            });
        }
    }
})();
