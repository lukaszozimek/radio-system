(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorChannelController', CorChannelController);

    CorChannelController.$inject = ['$scope', '$state', 'CorChannel'];

    function CorChannelController ($scope, $state, CorChannel) {
        var vm = this;

        vm.corChannels = [];

        loadAll();

        function loadAll() {
            CorChannel.query(function(result) {
                vm.corChannels = result;
                vm.searchQuery = null;
            });
        }
    }
})();
