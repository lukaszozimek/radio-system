(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TRAAdvertisementController', TRAAdvertisementController);

    TRAAdvertisementController.$inject = ['$scope', '$state', 'TRAAdvertisement'];

    function TRAAdvertisementController ($scope, $state, TRAAdvertisement) {
        var vm = this;

        vm.tRAAdvertisements = [];

        loadAll();

        function loadAll() {
            TRAAdvertisement.query(function(result) {
                vm.tRAAdvertisements = result;
                vm.searchQuery = null;
            });
        }
    }
})();
