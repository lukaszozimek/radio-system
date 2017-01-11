(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORNetworkController', CORNetworkController);

    CORNetworkController.$inject = ['$scope', '$state', 'CORNetwork'];

    function CORNetworkController ($scope, $state, CORNetwork) {
        var vm = this;

        vm.cORNetworks = [];

        loadAll();

        function loadAll() {
            CORNetwork.query(function(result) {
                vm.cORNetworks = result;
                vm.searchQuery = null;
            });
        }
    }
})();
