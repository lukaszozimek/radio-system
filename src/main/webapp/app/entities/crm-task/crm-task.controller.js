(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmTaskController', CrmTaskController);

    CrmTaskController.$inject = ['$scope', '$state', 'CrmTask'];

    function CrmTaskController ($scope, $state, CrmTask) {
        var vm = this;

        vm.crmTasks = [];

        loadAll();

        function loadAll() {
            CrmTask.query(function(result) {
                vm.crmTasks = result;
                vm.searchQuery = null;
            });
        }
    }
})();
