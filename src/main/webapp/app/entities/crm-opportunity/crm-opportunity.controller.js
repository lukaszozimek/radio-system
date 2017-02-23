(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmOpportunityController', CrmOpportunityController);

    CrmOpportunityController.$inject = ['$scope', '$state', 'CrmOpportunity'];

    function CrmOpportunityController ($scope, $state, CrmOpportunity) {
        var vm = this;

        vm.crmOpportunities = [];

        loadAll();

        function loadAll() {
            CrmOpportunity.query(function(result) {
                vm.crmOpportunities = result;
                vm.searchQuery = null;
            });
        }
    }
})();
