(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorCurrencyController', CorCurrencyController);

    CorCurrencyController.$inject = ['$scope', '$state', 'CorCurrency'];

    function CorCurrencyController ($scope, $state, CorCurrency) {
        var vm = this;

        vm.corCurrencies = [];

        loadAll();

        function loadAll() {
            CorCurrency.query(function(result) {
                vm.corCurrencies = result;
                vm.searchQuery = null;
            });
        }
    }
})();
