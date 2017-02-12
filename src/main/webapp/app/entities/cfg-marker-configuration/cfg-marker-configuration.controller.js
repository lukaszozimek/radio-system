(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CfgMarkerConfigurationController', CfgMarkerConfigurationController);

    CfgMarkerConfigurationController.$inject = ['$scope', '$state', 'CfgMarkerConfiguration'];

    function CfgMarkerConfigurationController ($scope, $state, CfgMarkerConfiguration) {
        var vm = this;

        vm.cfgMarkerConfigurations = [];

        loadAll();

        function loadAll() {
            CfgMarkerConfiguration.query(function(result) {
                vm.cfgMarkerConfigurations = result;
                vm.searchQuery = null;
            });
        }
    }
})();
