(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraPriceController', TraPriceController);

    TraPriceController.$inject = ['$scope', '$state', 'TraPrice'];

    function TraPriceController ($scope, $state, TraPrice) {
        var vm = this;

        vm.traPrices = [];

        loadAll();

        function loadAll() {
            TraPrice.query(function(result) {
                vm.traPrices = result;
                vm.searchQuery = null;
            });
        }
    }
})();
