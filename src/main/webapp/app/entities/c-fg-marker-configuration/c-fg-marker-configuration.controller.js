(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CFGMarkerConfigurationController', CFGMarkerConfigurationController);

    CFGMarkerConfigurationController.$inject = ['$scope', '$state', 'CFGMarkerConfiguration'];

    function CFGMarkerConfigurationController ($scope, $state, CFGMarkerConfiguration) {
        var vm = this;

        vm.cFGMarkerConfigurations = [];

        loadAll();

        function loadAll() {
            CFGMarkerConfiguration.query(function(result) {
                vm.cFGMarkerConfigurations = result;
                vm.searchQuery = null;
            });
        }
    }
})();
