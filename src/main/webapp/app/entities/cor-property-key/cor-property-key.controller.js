(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorPropertyKeyController', CorPropertyKeyController);

    CorPropertyKeyController.$inject = ['$scope', '$state', 'CorPropertyKey'];

    function CorPropertyKeyController ($scope, $state, CorPropertyKey) {
        var vm = this;

        vm.corPropertyKeys = [];

        loadAll();

        function loadAll() {
            CorPropertyKey.query(function(result) {
                vm.corPropertyKeys = result;
                vm.searchQuery = null;
            });
        }
    }
})();
