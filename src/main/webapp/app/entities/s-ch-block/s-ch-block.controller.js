(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('SCHBlockController', SCHBlockController);

    SCHBlockController.$inject = ['$scope', '$state', 'SCHBlock'];

    function SCHBlockController ($scope, $state, SCHBlock) {
        var vm = this;

        vm.sCHBlocks = [];

        loadAll();

        function loadAll() {
            SCHBlock.query(function(result) {
                vm.sCHBlocks = result;
                vm.searchQuery = null;
            });
        }
    }
})();
