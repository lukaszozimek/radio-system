(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CRMContactController', CRMContactController);

    CRMContactController.$inject = ['$scope', '$state', 'CRMContact'];

    function CRMContactController ($scope, $state, CRMContact) {
        var vm = this;

        vm.cRMContacts = [];

        loadAll();

        function loadAll() {
            CRMContact.query(function(result) {
                vm.cRMContacts = result;
                vm.searchQuery = null;
            });
        }
    }
})();
