(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraInvoiceStatusController', TraInvoiceStatusController);

    TraInvoiceStatusController.$inject = ['$scope', '$state', 'TraInvoiceStatus'];

    function TraInvoiceStatusController ($scope, $state, TraInvoiceStatus) {
        var vm = this;

        vm.traInvoiceStatuses = [];

        loadAll();

        function loadAll() {
            TraInvoiceStatus.query(function(result) {
                vm.traInvoiceStatuses = result;
                vm.searchQuery = null;
            });
        }
    }
})();
