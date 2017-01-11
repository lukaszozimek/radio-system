(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORSizeController', CORSizeController);

    CORSizeController.$inject = ['$scope', '$state', 'CORSize'];

    function CORSizeController ($scope, $state, CORSize) {
        var vm = this;

        vm.cORSizes = [];

        loadAll();

        function loadAll() {
            CORSize.query(function(result) {
                vm.cORSizes = result;
                vm.searchQuery = null;
            });
        }
    }
})();
