(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBMarkerController', LIBMarkerController);

    LIBMarkerController.$inject = ['$scope', '$state', 'LIBMarker'];

    function LIBMarkerController ($scope, $state, LIBMarker) {
        var vm = this;

        vm.lIBMarkers = [];

        loadAll();

        function loadAll() {
            LIBMarker.query(function(result) {
                vm.lIBMarkers = result;
                vm.searchQuery = null;
            });
        }
    }
})();
