(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibLabelController', LibLabelController);

    LibLabelController.$inject = ['$scope', '$state', 'LibLabel'];

    function LibLabelController ($scope, $state, LibLabel) {
        var vm = this;

        vm.libLabels = [];

        loadAll();

        function loadAll() {
            LibLabel.query(function(result) {
                vm.libLabels = result;
                vm.searchQuery = null;
            });
        }
    }
})();
