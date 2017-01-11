(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBAudioObjectController', LIBAudioObjectController);

    LIBAudioObjectController.$inject = ['$scope', '$state', 'LIBAudioObject'];

    function LIBAudioObjectController ($scope, $state, LIBAudioObject) {
        var vm = this;

        vm.lIBAudioObjects = [];

        loadAll();

        function loadAll() {
            LIBAudioObject.query(function(result) {
                vm.lIBAudioObjects = result;
                vm.searchQuery = null;
            });
        }
    }
})();
