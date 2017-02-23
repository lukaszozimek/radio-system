(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorSubscriptionController', CorSubscriptionController);

    CorSubscriptionController.$inject = ['$scope', '$state', 'CorSubscription'];

    function CorSubscriptionController ($scope, $state, CorSubscription) {
        var vm = this;

        vm.corSubscriptions = [];

        loadAll();

        function loadAll() {
            CorSubscription.query(function(result) {
                vm.corSubscriptions = result;
                vm.searchQuery = null;
            });
        }
    }
})();
