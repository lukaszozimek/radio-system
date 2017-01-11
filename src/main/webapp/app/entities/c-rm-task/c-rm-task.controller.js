(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CRMTaskController', CRMTaskController);

    CRMTaskController.$inject = ['$scope', '$state', 'CRMTask'];

    function CRMTaskController ($scope, $state, CRMTask) {
        var vm = this;

        vm.cRMTasks = [];

        loadAll();

        function loadAll() {
            CRMTask.query(function(result) {
                vm.cRMTasks = result;
                vm.searchQuery = null;
            });
        }
    }
})();
