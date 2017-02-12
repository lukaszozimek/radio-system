(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibImageItemController', LibImageItemController);

    LibImageItemController.$inject = ['$scope', '$state', 'LibImageItem'];

    function LibImageItemController ($scope, $state, LibImageItem) {
        var vm = this;

        vm.libImageItems = [];

        loadAll();

        function loadAll() {
            LibImageItem.query(function(result) {
                vm.libImageItems = result;
                vm.searchQuery = null;
            });
        }
    }
})();
