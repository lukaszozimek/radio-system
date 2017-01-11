(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORChannelController', CORChannelController);

    CORChannelController.$inject = ['$scope', '$state', 'CORChannel'];

    function CORChannelController ($scope, $state, CORChannel) {
        var vm = this;

        vm.cORChannels = [];

        loadAll();

        function loadAll() {
            CORChannel.query(function(result) {
                vm.cORChannels = result;
                vm.searchQuery = null;
            });
        }
    }
})();
