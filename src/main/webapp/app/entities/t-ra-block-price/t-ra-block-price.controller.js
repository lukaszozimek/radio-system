(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TRABlockPriceController', TRABlockPriceController);

    TRABlockPriceController.$inject = ['$scope', '$state', 'TRABlockPrice'];

    function TRABlockPriceController ($scope, $state, TRABlockPrice) {
        var vm = this;

        vm.tRABlockPrices = [];

        loadAll();

        function loadAll() {
            TRABlockPrice.query(function(result) {
                vm.tRABlockPrices = result;
                vm.searchQuery = null;
            });
        }
    }
})();
