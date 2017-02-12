(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorStatusController', CorStatusController);

    CorStatusController.$inject = ['$scope', '$state', 'CorStatus'];

    function CorStatusController ($scope, $state, CorStatus) {
        var vm = this;

        vm.corStatuses = [];

        loadAll();

        function loadAll() {
            CorStatus.query(function(result) {
                vm.corStatuses = result;
                vm.searchQuery = null;
            });
        }
    }
})();
