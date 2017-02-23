(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmLeadController', CrmLeadController);

    CrmLeadController.$inject = ['$scope', '$state', 'CrmLead'];

    function CrmLeadController ($scope, $state, CrmLead) {
        var vm = this;

        vm.crmLeads = [];

        loadAll();

        function loadAll() {
            CrmLead.query(function(result) {
                vm.crmLeads = result;
                vm.searchQuery = null;
            });
        }
    }
})();
