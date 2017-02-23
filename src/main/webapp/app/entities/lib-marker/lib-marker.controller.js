(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibMarkerController', LibMarkerController);

    LibMarkerController.$inject = ['$scope', '$state', 'LibMarker'];

    function LibMarkerController ($scope, $state, LibMarker) {
        var vm = this;

        vm.libMarkers = [];

        loadAll();

        function loadAll() {
            LibMarker.query(function(result) {
                vm.libMarkers = result;
                vm.searchQuery = null;
            });
        }
    }
})();
