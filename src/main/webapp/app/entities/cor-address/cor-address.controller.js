(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorAddressController', CorAddressController);

    CorAddressController.$inject = ['$scope', '$state', 'CorAddress'];

    function CorAddressController ($scope, $state, CorAddress) {
        var vm = this;

        vm.corAddresses = [];

        loadAll();

        function loadAll() {
            CorAddress.query(function(result) {
                vm.corAddresses = result;
                vm.searchQuery = null;
            });
        }
    }
})();
