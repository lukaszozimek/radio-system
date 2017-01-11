(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CRMLeadStatusController', CRMLeadStatusController);

    CRMLeadStatusController.$inject = ['$scope', '$state', 'CRMLeadStatus'];

    function CRMLeadStatusController ($scope, $state, CRMLeadStatus) {
        var vm = this;

        vm.cRMLeadStatuses = [];

        loadAll();

        function loadAll() {
            CRMLeadStatus.query(function(result) {
                vm.cRMLeadStatuses = result;
                vm.searchQuery = null;
            });
        }
    }
})();
