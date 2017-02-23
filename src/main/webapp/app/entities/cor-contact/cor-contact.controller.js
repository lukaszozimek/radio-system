(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorContactController', CorContactController);

    CorContactController.$inject = ['$scope', '$state', 'CorContact'];

    function CorContactController ($scope, $state, CorContact) {
        var vm = this;

        vm.corContacts = [];

        loadAll();

        function loadAll() {
            CorContact.query(function(result) {
                vm.corContacts = result;
                vm.searchQuery = null;
            });
        }
    }
})();
