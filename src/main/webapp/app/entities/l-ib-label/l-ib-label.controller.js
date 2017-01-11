(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBLabelController', LIBLabelController);

    LIBLabelController.$inject = ['$scope', '$state', 'LIBLabel'];

    function LIBLabelController ($scope, $state, LIBLabel) {
        var vm = this;

        vm.lIBLabels = [];

        loadAll();

        function loadAll() {
            LIBLabel.query(function(result) {
                vm.lIBLabels = result;
                vm.searchQuery = null;
            });
        }
    }
})();
