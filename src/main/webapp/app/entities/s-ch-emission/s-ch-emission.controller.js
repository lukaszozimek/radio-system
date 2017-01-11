(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('SCHEmissionController', SCHEmissionController);

    SCHEmissionController.$inject = ['$scope', '$state', 'SCHEmission'];

    function SCHEmissionController ($scope, $state, SCHEmission) {
        var vm = this;

        vm.sCHEmissions = [];

        loadAll();

        function loadAll() {
            SCHEmission.query(function(result) {
                vm.sCHEmissions = result;
                vm.searchQuery = null;
            });
        }
    }
})();
