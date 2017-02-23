(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibMediaItemController', LibMediaItemController);

    LibMediaItemController.$inject = ['$scope', '$state', 'LibMediaItem'];

    function LibMediaItemController ($scope, $state, LibMediaItem) {
        var vm = this;

        vm.libMediaItems = [];

        loadAll();

        function loadAll() {
            LibMediaItem.query(function(result) {
                vm.libMediaItems = result;
                vm.searchQuery = null;
            });
        }
    }
})();
