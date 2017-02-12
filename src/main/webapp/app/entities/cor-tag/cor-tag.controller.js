(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorTagController', CorTagController);

    CorTagController.$inject = ['$scope', '$state', 'CorTag'];

    function CorTagController ($scope, $state, CorTag) {
        var vm = this;

        vm.corTags = [];

        loadAll();

        function loadAll() {
            CorTag.query(function(result) {
                vm.corTags = result;
                vm.searchQuery = null;
            });
        }
    }
})();
