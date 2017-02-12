(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibCloudObjectController', LibCloudObjectController);

    LibCloudObjectController.$inject = ['$scope', '$state', 'LibCloudObject'];

    function LibCloudObjectController ($scope, $state, LibCloudObject) {
        var vm = this;

        vm.libCloudObjects = [];

        loadAll();

        function loadAll() {
            LibCloudObject.query(function(result) {
                vm.libCloudObjects = result;
                vm.searchQuery = null;
            });
        }
    }
})();
