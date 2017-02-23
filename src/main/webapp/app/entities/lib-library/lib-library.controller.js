(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibLibraryController', LibLibraryController);

    LibLibraryController.$inject = ['$scope', '$state', 'LibLibrary'];

    function LibLibraryController ($scope, $state, LibLibrary) {
        var vm = this;

        vm.libLibraries = [];

        loadAll();

        function loadAll() {
            LibLibrary.query(function(result) {
                vm.libLibraries = result;
                vm.searchQuery = null;
            });
        }
    }
})();
