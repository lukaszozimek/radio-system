(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORPropertyValueController', CORPropertyValueController);

    CORPropertyValueController.$inject = ['$scope', '$state', 'CORPropertyValue'];

    function CORPropertyValueController ($scope, $state, CORPropertyValue) {
        var vm = this;

        vm.cORPropertyValues = [];

        loadAll();

        function loadAll() {
            CORPropertyValue.query(function(result) {
                vm.cORPropertyValues = result;
                vm.searchQuery = null;
            });
        }
    }
})();
