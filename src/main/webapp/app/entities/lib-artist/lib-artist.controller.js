(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibArtistController', LibArtistController);

    LibArtistController.$inject = ['$scope', '$state', 'LibArtist'];

    function LibArtistController ($scope, $state, LibArtist) {
        var vm = this;

        vm.libArtists = [];

        loadAll();

        function loadAll() {
            LibArtist.query(function(result) {
                vm.libArtists = result;
                vm.searchQuery = null;
            });
        }
    }
})();
