(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TRAPriceController', TRAPriceController);

    TRAPriceController.$inject = ['$scope', '$state', 'TRAPrice'];

    function TRAPriceController ($scope, $state, TRAPrice) {
        var vm = this;

        vm.tRAPrices = [];

        loadAll();

        function loadAll() {
            TRAPrice.query(function(result) {
                vm.tRAPrices = result;
                vm.searchQuery = null;
            });
        }
    }
})();
