(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorUserController', CorUserController);

    CorUserController.$inject = ['$scope', '$state', 'CorUser'];

    function CorUserController ($scope, $state, CorUser) {
        var vm = this;

        vm.corUsers = [];

        loadAll();

        function loadAll() {
            CorUser.query(function(result) {
                vm.corUsers = result;
                vm.searchQuery = null;
            });
        }
    }
})();
