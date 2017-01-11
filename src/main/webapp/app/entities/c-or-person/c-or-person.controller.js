(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORPersonController', CORPersonController);

    CORPersonController.$inject = ['$scope', '$state', 'CORPerson'];

    function CORPersonController ($scope, $state, CORPerson) {
        var vm = this;

        vm.cORPeople = [];

        loadAll();

        function loadAll() {
            CORPerson.query(function(result) {
                vm.cORPeople = result;
                vm.searchQuery = null;
            });
        }
    }
})();
