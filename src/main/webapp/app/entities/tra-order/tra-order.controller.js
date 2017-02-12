(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraOrderController', TraOrderController);

    TraOrderController.$inject = ['$scope', '$state', 'TraOrder'];

    function TraOrderController ($scope, $state, TraOrder) {
        var vm = this;

        vm.traOrders = [];

        loadAll();

        function loadAll() {
            TraOrder.query(function(result) {
                vm.traOrders = result;
                vm.searchQuery = null;
            });
        }
    }
})();
