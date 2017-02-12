(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('SchEmissionController', SchEmissionController);

    SchEmissionController.$inject = ['$scope', '$state', 'SchEmission'];

    function SchEmissionController ($scope, $state, SchEmission) {
        var vm = this;

        vm.schEmissions = [];

        loadAll();

        function loadAll() {
            SchEmission.query(function(result) {
                vm.schEmissions = result;
                vm.searchQuery = null;
            });
        }
    }
})();
