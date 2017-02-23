(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibImageObjectController', LibImageObjectController);

    LibImageObjectController.$inject = ['$scope', '$state', 'LibImageObject'];

    function LibImageObjectController ($scope, $state, LibImageObject) {
        var vm = this;

        vm.libImageObjects = [];

        loadAll();

        function loadAll() {
            LibImageObject.query(function(result) {
                vm.libImageObjects = result;
                vm.searchQuery = null;
            });
        }
    }
})();
