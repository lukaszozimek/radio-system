(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraCampaingStatusController', TraCampaingStatusController);

    TraCampaingStatusController.$inject = ['$scope', '$state', 'TraCampaingStatus'];

    function TraCampaingStatusController ($scope, $state, TraCampaingStatus) {
        var vm = this;

        vm.traCampaingStatuses = [];

        loadAll();

        function loadAll() {
            TraCampaingStatus.query(function(result) {
                vm.traCampaingStatuses = result;
                vm.searchQuery = null;
            });
        }
    }
})();
