(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TRAIndustryController', TRAIndustryController);

    TRAIndustryController.$inject = ['$scope', '$state', 'TRAIndustry'];

    function TRAIndustryController ($scope, $state, TRAIndustry) {
        var vm = this;

        vm.tRAIndustries = [];

        loadAll();

        function loadAll() {
            TRAIndustry.query(function(result) {
                vm.tRAIndustries = result;
                vm.searchQuery = null;
            });
        }
    }
})();
