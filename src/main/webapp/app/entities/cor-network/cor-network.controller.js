(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorNetworkController', CorNetworkController);

    CorNetworkController.$inject = ['$scope', '$state', 'CorNetwork'];

    function CorNetworkController ($scope, $state, CorNetwork) {
        var vm = this;

        vm.corNetworks = [];

        loadAll();

        function loadAll() {
            CorNetwork.query(function(result) {
                vm.corNetworks = result;
                vm.searchQuery = null;
            });
        }
    }
})();
