(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmTaskStatusController', CrmTaskStatusController);

    CrmTaskStatusController.$inject = ['$scope', '$state', 'CrmTaskStatus'];

    function CrmTaskStatusController ($scope, $state, CrmTaskStatus) {
        var vm = this;

        vm.crmTaskStatuses = [];

        loadAll();

        function loadAll() {
            CrmTaskStatus.query(function(result) {
                vm.crmTaskStatuses = result;
                vm.searchQuery = null;
            });
        }
    }
})();
