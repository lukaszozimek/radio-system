(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBMediaItemController', LIBMediaItemController);

    LIBMediaItemController.$inject = ['$scope', '$state', 'LIBMediaItem'];

    function LIBMediaItemController ($scope, $state, LIBMediaItem) {
        var vm = this;

        vm.lIBMediaItems = [];

        loadAll();

        function loadAll() {
            LIBMediaItem.query(function(result) {
                vm.lIBMediaItems = result;
                vm.searchQuery = null;
            });
        }
    }
})();
