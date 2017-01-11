(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TRACampaignController', TRACampaignController);

    TRACampaignController.$inject = ['$scope', '$state', 'TRACampaign'];

    function TRACampaignController ($scope, $state, TRACampaign) {
        var vm = this;

        vm.tRACampaigns = [];

        loadAll();

        function loadAll() {
            TRACampaign.query(function(result) {
                vm.tRACampaigns = result;
                vm.searchQuery = null;
            });
        }
    }
})();
