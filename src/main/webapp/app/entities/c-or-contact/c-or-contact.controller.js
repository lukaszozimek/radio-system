(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORContactController', CORContactController);

    CORContactController.$inject = ['$scope', '$state', 'CORContact'];

    function CORContactController ($scope, $state, CORContact) {
        var vm = this;

        vm.cORContacts = [];

        loadAll();

        function loadAll() {
            CORContact.query(function(result) {
                vm.cORContacts = result;
                vm.searchQuery = null;
            });
        }
    }
})();
