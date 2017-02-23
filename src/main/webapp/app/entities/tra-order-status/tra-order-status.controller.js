(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraOrderStatusController', TraOrderStatusController);

    TraOrderStatusController.$inject = ['$scope', '$state', 'TraOrderStatus'];

    function TraOrderStatusController ($scope, $state, TraOrderStatus) {
        var vm = this;

        vm.traOrderStatuses = [];

        loadAll();

        function loadAll() {
            TraOrderStatus.query(function(result) {
                vm.traOrderStatuses = result;
                vm.searchQuery = null;
            });
        }
    }
})();
