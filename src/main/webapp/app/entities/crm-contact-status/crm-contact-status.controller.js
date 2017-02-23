(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmContactStatusController', CrmContactStatusController);

    CrmContactStatusController.$inject = ['$scope', '$state', 'CrmContactStatus'];

    function CrmContactStatusController ($scope, $state, CrmContactStatus) {
        var vm = this;

        vm.crmContactStatuses = [];

        loadAll();

        function loadAll() {
            CrmContactStatus.query(function(result) {
                vm.crmContactStatuses = result;
                vm.searchQuery = null;
            });
        }
    }
})();
