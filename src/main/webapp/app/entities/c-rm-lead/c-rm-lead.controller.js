(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CRMLeadController', CRMLeadController);

    CRMLeadController.$inject = ['$scope', '$state', 'CRMLead'];

    function CRMLeadController ($scope, $state, CRMLead) {
        var vm = this;

        vm.cRMLeads = [];

        loadAll();

        function loadAll() {
            CRMLead.query(function(result) {
                vm.cRMLeads = result;
                vm.searchQuery = null;
            });
        }
    }
})();
