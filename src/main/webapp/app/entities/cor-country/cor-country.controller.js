(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorCountryController', CorCountryController);

    CorCountryController.$inject = ['$scope', '$state', 'CorCountry'];

    function CorCountryController ($scope, $state, CorCountry) {
        var vm = this;

        vm.corCountries = [];

        loadAll();

        function loadAll() {
            CorCountry.query(function(result) {
                vm.corCountries = result;
                vm.searchQuery = null;
            });
        }
    }
})();
