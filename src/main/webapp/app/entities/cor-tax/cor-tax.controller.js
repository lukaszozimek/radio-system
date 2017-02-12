(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorTaxController', CorTaxController);

    CorTaxController.$inject = ['$scope', '$state', 'CorTax'];

    function CorTaxController ($scope, $state, CorTax) {
        var vm = this;

        vm.corTaxes = [];

        loadAll();

        function loadAll() {
            CorTax.query(function(result) {
                vm.corTaxes = result;
                vm.searchQuery = null;
            });
        }
    }
})();
