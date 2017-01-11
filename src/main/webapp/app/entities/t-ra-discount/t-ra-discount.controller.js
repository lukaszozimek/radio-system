(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TRADiscountController', TRADiscountController);

    TRADiscountController.$inject = ['$scope', '$state', 'TRADiscount'];

    function TRADiscountController ($scope, $state, TRADiscount) {
        var vm = this;

        vm.tRADiscounts = [];

        loadAll();

        function loadAll() {
            TRADiscount.query(function(result) {
                vm.tRADiscounts = result;
                vm.searchQuery = null;
            });
        }
    }
})();
