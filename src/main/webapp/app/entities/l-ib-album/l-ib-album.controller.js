(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBAlbumController', LIBAlbumController);

    LIBAlbumController.$inject = ['$scope', '$state', 'LIBAlbum'];

    function LIBAlbumController ($scope, $state, LIBAlbum) {
        var vm = this;

        vm.lIBAlbums = [];

        loadAll();

        function loadAll() {
            LIBAlbum.query(function(result) {
                vm.lIBAlbums = result;
                vm.searchQuery = null;
            });
        }
    }
})();
