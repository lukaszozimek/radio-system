(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorRangeController', CorRangeController);

    CorRangeController.$inject = ['$scope', '$state', 'CorRange'];

    function CorRangeController ($scope, $state, CorRange) {
        var vm = this;

        vm.corRanges = [];

        loadAll();

        function loadAll() {
            CorRange.query(function(result) {
                vm.corRanges = result;
                vm.searchQuery = null;
            });
        }
    }
})();
