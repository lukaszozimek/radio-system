(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBImageItemController', LIBImageItemController);

    LIBImageItemController.$inject = ['$scope', '$state', 'LIBImageItem'];

    function LIBImageItemController ($scope, $state, LIBImageItem) {
        var vm = this;

        vm.lIBImageItems = [];

        loadAll();

        function loadAll() {
            LIBImageItem.query(function(result) {
                vm.lIBImageItems = result;
                vm.searchQuery = null;
            });
        }
    }
})();
