(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TRAOrderController', TRAOrderController);

    TRAOrderController.$inject = ['$scope', '$state', 'TRAOrder'];

    function TRAOrderController ($scope, $state, TRAOrder) {
        var vm = this;

        vm.tRAOrders = [];

        loadAll();

        function loadAll() {
            TRAOrder.query(function(result) {
                vm.tRAOrders = result;
                vm.searchQuery = null;
            });
        }
    }
})();
