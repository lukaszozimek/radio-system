(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CRMTaskStatusController', CRMTaskStatusController);

    CRMTaskStatusController.$inject = ['$scope', '$state', 'CRMTaskStatus'];

    function CRMTaskStatusController ($scope, $state, CRMTaskStatus) {
        var vm = this;

        vm.cRMTaskStatuses = [];

        loadAll();

        function loadAll() {
            CRMTaskStatus.query(function(result) {
                vm.cRMTaskStatuses = result;
                vm.searchQuery = null;
            });
        }
    }
})();
