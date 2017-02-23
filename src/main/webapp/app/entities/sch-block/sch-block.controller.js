(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('SchBlockController', SchBlockController);

    SchBlockController.$inject = ['$scope', '$state', 'SchBlock'];

    function SchBlockController ($scope, $state, SchBlock) {
        var vm = this;

        vm.schBlocks = [];

        loadAll();

        function loadAll() {
            SchBlock.query(function(result) {
                vm.schBlocks = result;
                vm.searchQuery = null;
            });
        }
    }
})();
