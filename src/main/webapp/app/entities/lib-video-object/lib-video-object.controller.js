(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibVideoObjectController', LibVideoObjectController);

    LibVideoObjectController.$inject = ['$scope', '$state', 'LibVideoObject'];

    function LibVideoObjectController ($scope, $state, LibVideoObject) {
        var vm = this;

        vm.libVideoObjects = [];

        loadAll();

        function loadAll() {
            LibVideoObject.query(function(result) {
                vm.libVideoObjects = result;
                vm.searchQuery = null;
            });
        }
    }
})();
