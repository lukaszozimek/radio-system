(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraAdvertismentTypeController', TraAdvertismentTypeController);

    TraAdvertismentTypeController.$inject = ['$scope', '$state', 'TraAdvertismentType'];

    function TraAdvertismentTypeController ($scope, $state, TraAdvertismentType) {
        var vm = this;

        vm.traAdvertismentTypes = [];

        loadAll();

        function loadAll() {
            TraAdvertismentType.query(function(result) {
                vm.traAdvertismentTypes = result;
                vm.searchQuery = null;
            });
        }
    }
})();
