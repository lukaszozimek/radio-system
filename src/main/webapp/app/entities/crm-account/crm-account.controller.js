(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmAccountController', CrmAccountController);

    CrmAccountController.$inject = ['$scope', '$state', 'CrmAccount'];

    function CrmAccountController ($scope, $state, CrmAccount) {
        var vm = this;

        vm.crmAccounts = [];

        loadAll();

        function loadAll() {
            CrmAccount.query(function(result) {
                vm.crmAccounts = result;
                vm.searchQuery = null;
            });
        }
    }
})();
