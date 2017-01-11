(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBCloudObjectController', LIBCloudObjectController);

    LIBCloudObjectController.$inject = ['$scope', '$state', 'LIBCloudObject'];

    function LIBCloudObjectController ($scope, $state, LIBCloudObject) {
        var vm = this;

        vm.lIBCloudObjects = [];

        loadAll();

        function loadAll() {
            LIBCloudObject.query(function(result) {
                vm.lIBCloudObjects = result;
                vm.searchQuery = null;
            });
        }
    }
})();
