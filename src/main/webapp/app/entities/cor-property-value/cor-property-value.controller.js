(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorPropertyValueController', CorPropertyValueController);

    CorPropertyValueController.$inject = ['$scope', '$state', 'CorPropertyValue'];

    function CorPropertyValueController ($scope, $state, CorPropertyValue) {
        var vm = this;

        vm.corPropertyValues = [];

        loadAll();

        function loadAll() {
            CorPropertyValue.query(function(result) {
                vm.corPropertyValues = result;
                vm.searchQuery = null;
            });
        }
    }
})();
