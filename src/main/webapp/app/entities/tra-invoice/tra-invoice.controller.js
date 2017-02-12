(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraInvoiceController', TraInvoiceController);

    TraInvoiceController.$inject = ['$scope', '$state', 'TraInvoice'];

    function TraInvoiceController ($scope, $state, TraInvoice) {
        var vm = this;

        vm.traInvoices = [];

        loadAll();

        function loadAll() {
            TraInvoice.query(function(result) {
                vm.traInvoices = result;
                vm.searchQuery = null;
            });
        }
    }
})();
