(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TRAEmissionOrderController', TRAEmissionOrderController);

    TRAEmissionOrderController.$inject = ['$scope', '$state', 'TRAEmissionOrder'];

    function TRAEmissionOrderController ($scope, $state, TRAEmissionOrder) {
        var vm = this;

        vm.tRAEmissionOrders = [];

        loadAll();

        function loadAll() {
            TRAEmissionOrder.query(function(result) {
                vm.tRAEmissionOrders = result;
                vm.searchQuery = null;
            });
        }
    }
})();
