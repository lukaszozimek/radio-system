(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBTrackController', LIBTrackController);

    LIBTrackController.$inject = ['$scope', '$state', 'LIBTrack'];

    function LIBTrackController ($scope, $state, LIBTrack) {
        var vm = this;

        vm.lIBTracks = [];

        loadAll();

        function loadAll() {
            LIBTrack.query(function(result) {
                vm.lIBTracks = result;
                vm.searchQuery = null;
            });
        }
    }
})();
