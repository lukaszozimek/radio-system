(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORRangeController', CORRangeController);

    CORRangeController.$inject = ['$scope', '$state', 'CORRange'];

    function CORRangeController ($scope, $state, CORRange) {
        var vm = this;

        vm.cORRanges = [];

        loadAll();

        function loadAll() {
            CORRange.query(function(result) {
                vm.cORRanges = result;
                vm.searchQuery = null;
            });
        }
    }
})();
