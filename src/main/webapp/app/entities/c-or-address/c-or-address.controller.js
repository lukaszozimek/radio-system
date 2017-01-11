(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORAddressController', CORAddressController);

    CORAddressController.$inject = ['$scope', '$state', 'CORAddress'];

    function CORAddressController ($scope, $state, CORAddress) {
        var vm = this;

        vm.cORAddresses = [];

        loadAll();

        function loadAll() {
            CORAddress.query(function(result) {
                vm.cORAddresses = result;
                vm.searchQuery = null;
            });
        }
    }
})();
