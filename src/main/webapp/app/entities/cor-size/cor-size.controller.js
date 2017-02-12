(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorSizeController', CorSizeController);

    CorSizeController.$inject = ['$scope', '$state', 'CorSize'];

    function CorSizeController ($scope, $state, CorSize) {
        var vm = this;

        vm.corSizes = [];

        loadAll();

        function loadAll() {
            CorSize.query(function(result) {
                vm.corSizes = result;
                vm.searchQuery = null;
            });
        }
    }
})();
