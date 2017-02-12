(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmLeadStatusController', CrmLeadStatusController);

    CrmLeadStatusController.$inject = ['$scope', '$state', 'CrmLeadStatus'];

    function CrmLeadStatusController ($scope, $state, CrmLeadStatus) {
        var vm = this;

        vm.crmLeadStatuses = [];

        loadAll();

        function loadAll() {
            CrmLeadStatus.query(function(result) {
                vm.crmLeadStatuses = result;
                vm.searchQuery = null;
            });
        }
    }
})();
