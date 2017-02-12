(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraDiscountController', TraDiscountController);

    TraDiscountController.$inject = ['$scope', '$state', 'TraDiscount'];

    function TraDiscountController ($scope, $state, TraDiscount) {
        var vm = this;

        vm.traDiscounts = [];

        loadAll();

        function loadAll() {
            TraDiscount.query(function(result) {
                vm.traDiscounts = result;
                vm.searchQuery = null;
            });
        }
    }
})();
