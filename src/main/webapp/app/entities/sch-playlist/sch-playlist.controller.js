(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('SchPlaylistController', SchPlaylistController);

    SchPlaylistController.$inject = ['$scope', '$state', 'SchPlaylist'];

    function SchPlaylistController ($scope, $state, SchPlaylist) {
        var vm = this;

        vm.schPlaylists = [];

        loadAll();

        function loadAll() {
            SchPlaylist.query(function(result) {
                vm.schPlaylists = result;
                vm.searchQuery = null;
            });
        }
    }
})();
