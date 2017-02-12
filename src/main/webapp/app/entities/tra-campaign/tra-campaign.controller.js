(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraCampaignController', TraCampaignController);

    TraCampaignController.$inject = ['$scope', '$state', 'TraCampaign'];

    function TraCampaignController ($scope, $state, TraCampaign) {
        var vm = this;

        vm.traCampaigns = [];

        loadAll();

        function loadAll() {
            TraCampaign.query(function(result) {
                vm.traCampaigns = result;
                vm.searchQuery = null;
            });
        }
    }
})();
