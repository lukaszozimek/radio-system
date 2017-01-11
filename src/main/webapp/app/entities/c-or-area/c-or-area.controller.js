(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORAreaController', CORAreaController);

    CORAreaController.$inject = ['$scope', '$state', 'CORArea'];

    function CORAreaController ($scope, $state, CORArea) {
        var vm = this;

        vm.cORAreas = [];

        loadAll();

        function loadAll() {
            CORArea.query(function(result) {
                vm.cORAreas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
