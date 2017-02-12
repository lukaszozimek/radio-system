(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraAdvertisementController', TraAdvertisementController);

    TraAdvertisementController.$inject = ['$scope', '$state', 'TraAdvertisement'];

    function TraAdvertisementController ($scope, $state, TraAdvertisement) {
        var vm = this;

        vm.traAdvertisements = [];

        loadAll();

        function loadAll() {
            TraAdvertisement.query(function(result) {
                vm.traAdvertisements = result;
                vm.searchQuery = null;
            });
        }
    }
})();
