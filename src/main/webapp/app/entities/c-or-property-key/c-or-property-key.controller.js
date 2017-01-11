(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORPropertyKeyController', CORPropertyKeyController);

    CORPropertyKeyController.$inject = ['$scope', '$state', 'CORPropertyKey'];

    function CORPropertyKeyController ($scope, $state, CORPropertyKey) {
        var vm = this;

        vm.cORPropertyKeys = [];

        loadAll();

        function loadAll() {
            CORPropertyKey.query(function(result) {
                vm.cORPropertyKeys = result;
                vm.searchQuery = null;
            });
        }
    }
})();
