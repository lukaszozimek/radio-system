(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CRMAccountController', CRMAccountController);

    CRMAccountController.$inject = ['$scope', '$state', 'CRMAccount'];

    function CRMAccountController ($scope, $state, CRMAccount) {
        var vm = this;

        vm.cRMAccounts = [];

        loadAll();

        function loadAll() {
            CRMAccount.query(function(result) {
                vm.cRMAccounts = result;
                vm.searchQuery = null;
            });
        }
    }
})();
