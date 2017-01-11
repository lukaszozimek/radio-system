(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBVideoObjectController', LIBVideoObjectController);

    LIBVideoObjectController.$inject = ['$scope', '$state', 'LIBVideoObject'];

    function LIBVideoObjectController ($scope, $state, LIBVideoObject) {
        var vm = this;

        vm.lIBVideoObjects = [];

        loadAll();

        function loadAll() {
            LIBVideoObject.query(function(result) {
                vm.lIBVideoObjects = result;
                vm.searchQuery = null;
            });
        }
    }
})();
