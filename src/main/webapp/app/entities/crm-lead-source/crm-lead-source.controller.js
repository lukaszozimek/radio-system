(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmLeadSourceController', CrmLeadSourceController);

    CrmLeadSourceController.$inject = ['$scope', '$state', 'CrmLeadSource'];

    function CrmLeadSourceController ($scope, $state, CrmLeadSource) {
        var vm = this;

        vm.crmLeadSources = [];

        loadAll();

        function loadAll() {
            CrmLeadSource.query(function(result) {
                vm.crmLeadSources = result;
                vm.searchQuery = null;
            });
        }
    }
})();
