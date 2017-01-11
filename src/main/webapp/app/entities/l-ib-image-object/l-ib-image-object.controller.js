(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBImageObjectController', LIBImageObjectController);

    LIBImageObjectController.$inject = ['$scope', '$state', 'LIBImageObject'];

    function LIBImageObjectController ($scope, $state, LIBImageObject) {
        var vm = this;

        vm.lIBImageObjects = [];

        loadAll();

        function loadAll() {
            LIBImageObject.query(function(result) {
                vm.lIBImageObjects = result;
                vm.searchQuery = null;
            });
        }
    }
})();
