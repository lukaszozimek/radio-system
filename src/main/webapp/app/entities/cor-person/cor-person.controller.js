(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorPersonController', CorPersonController);

    CorPersonController.$inject = ['$scope', '$state', 'CorPerson'];

    function CorPersonController ($scope, $state, CorPerson) {
        var vm = this;

        vm.corPeople = [];

        loadAll();

        function loadAll() {
            CorPerson.query(function(result) {
                vm.corPeople = result;
                vm.searchQuery = null;
            });
        }
    }
})();
