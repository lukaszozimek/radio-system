(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TRAInvoiceController', TRAInvoiceController);

    TRAInvoiceController.$inject = ['$scope', '$state', 'TRAInvoice'];

    function TRAInvoiceController ($scope, $state, TRAInvoice) {
        var vm = this;

        vm.tRAInvoices = [];

        loadAll();

        function loadAll() {
            TRAInvoice.query(function(result) {
                vm.tRAInvoices = result;
                vm.searchQuery = null;
            });
        }
    }
})();
