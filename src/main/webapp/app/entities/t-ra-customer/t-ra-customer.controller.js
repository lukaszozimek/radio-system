(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TRACustomerController', TRACustomerController);

    TRACustomerController.$inject = ['$scope', '$state', 'TRACustomer'];

    function TRACustomerController ($scope, $state, TRACustomer) {
        var vm = this;

        vm.tRACustomers = [];

        loadAll();

        function loadAll() {
            TRACustomer.query(function(result) {
                vm.tRACustomers = result;
                vm.searchQuery = null;
            });
        }
    }
})();
