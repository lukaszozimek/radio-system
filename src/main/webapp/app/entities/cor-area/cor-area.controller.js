(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorAreaController', CorAreaController);

    CorAreaController.$inject = ['$scope', '$state', 'CorArea'];

    function CorAreaController ($scope, $state, CorArea) {
        var vm = this;

        vm.corAreas = [];

        loadAll();

        function loadAll() {
            CorArea.query(function(result) {
                vm.corAreas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
