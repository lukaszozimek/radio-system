(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibTrackController', LibTrackController);

    LibTrackController.$inject = ['$scope', '$state', 'LibTrack'];

    function LibTrackController ($scope, $state, LibTrack) {
        var vm = this;

        vm.libTracks = [];

        loadAll();

        function loadAll() {
            LibTrack.query(function(result) {
                vm.libTracks = result;
                vm.searchQuery = null;
            });
        }
    }
})();
