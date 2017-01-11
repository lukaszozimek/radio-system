(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBArtistController', LIBArtistController);

    LIBArtistController.$inject = ['$scope', '$state', 'LIBArtist'];

    function LIBArtistController ($scope, $state, LIBArtist) {
        var vm = this;

        vm.lIBArtists = [];

        loadAll();

        function loadAll() {
            LIBArtist.query(function(result) {
                vm.lIBArtists = result;
                vm.searchQuery = null;
            });
        }
    }
})();
