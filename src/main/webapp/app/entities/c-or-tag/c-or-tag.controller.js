(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORTagController', CORTagController);

    CORTagController.$inject = ['$scope', '$state', 'CORTag'];

    function CORTagController ($scope, $state, CORTag) {
        var vm = this;

        vm.cORTags = [];

        loadAll();

        function loadAll() {
            CORTag.query(function(result) {
                vm.cORTags = result;
                vm.searchQuery = null;
            });
        }
    }
})();
