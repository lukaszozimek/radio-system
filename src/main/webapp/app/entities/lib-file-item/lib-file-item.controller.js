(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibFileItemController', LibFileItemController);

    LibFileItemController.$inject = ['$scope', '$state', 'LibFileItem'];

    function LibFileItemController ($scope, $state, LibFileItem) {
        var vm = this;

        vm.libFileItems = [];

        loadAll();

        function loadAll() {
            LibFileItem.query(function(result) {
                vm.libFileItems = result;
                vm.searchQuery = null;
            });
        }
    }
})();
