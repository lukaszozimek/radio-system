(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBLibraryController', LIBLibraryController);

    LIBLibraryController.$inject = ['$scope', '$state', 'LIBLibrary'];

    function LIBLibraryController ($scope, $state, LIBLibrary) {
        var vm = this;

        vm.lIBLibraries = [];

        loadAll();

        function loadAll() {
            LIBLibrary.query(function(result) {
                vm.lIBLibraries = result;
                vm.searchQuery = null;
            });
        }
    }
})();
