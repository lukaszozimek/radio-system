(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraIndustryController', TraIndustryController);

    TraIndustryController.$inject = ['$scope', '$state', 'TraIndustry'];

    function TraIndustryController ($scope, $state, TraIndustry) {
        var vm = this;

        vm.traIndustries = [];

        loadAll();

        function loadAll() {
            TraIndustry.query(function(result) {
                vm.traIndustries = result;
                vm.searchQuery = null;
            });
        }
    }
})();
