(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmContactController', CrmContactController);

    CrmContactController.$inject = ['$scope', '$state', 'CrmContact'];

    function CrmContactController ($scope, $state, CrmContact) {
        var vm = this;

        vm.crmContacts = [];

        loadAll();

        function loadAll() {
            CrmContact.query(function(result) {
                vm.crmContacts = result;
                vm.searchQuery = null;
            });
        }
    }
})();
