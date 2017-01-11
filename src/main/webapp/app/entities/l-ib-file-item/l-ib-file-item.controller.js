(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBFileItemController', LIBFileItemController);

    LIBFileItemController.$inject = ['$scope', '$state', 'LIBFileItem'];

    function LIBFileItemController ($scope, $state, LIBFileItem) {
        var vm = this;

        vm.lIBFileItems = [];

        loadAll();

        function loadAll() {
            LIBFileItem.query(function(result) {
                vm.lIBFileItems = result;
                vm.searchQuery = null;
            });
        }
    }
})();
