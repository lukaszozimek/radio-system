(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CRMOpportunityController', CRMOpportunityController);

    CRMOpportunityController.$inject = ['$scope', '$state', 'CRMOpportunity'];

    function CRMOpportunityController ($scope, $state, CRMOpportunity) {
        var vm = this;

        vm.cRMOpportunities = [];

        loadAll();

        function loadAll() {
            CRMOpportunity.query(function(result) {
                vm.cRMOpportunities = result;
                vm.searchQuery = null;
            });
        }
    }
})();
