(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CRMLeadSourceController', CRMLeadSourceController);

    CRMLeadSourceController.$inject = ['$scope', '$state', 'CRMLeadSource'];

    function CRMLeadSourceController ($scope, $state, CRMLeadSource) {
        var vm = this;

        vm.cRMLeadSources = [];

        loadAll();

        function loadAll() {
            CRMLeadSource.query(function(result) {
                vm.cRMLeadSources = result;
                vm.searchQuery = null;
            });
        }
    }
})();
