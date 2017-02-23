(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('SchTemplateController', SchTemplateController);

    SchTemplateController.$inject = ['$scope', '$state', 'SchTemplate'];

    function SchTemplateController ($scope, $state, SchTemplate) {
        var vm = this;

        vm.schTemplates = [];

        loadAll();

        function loadAll() {
            SchTemplate.query(function(result) {
                vm.schTemplates = result;
                vm.searchQuery = null;
            });
        }
    }
})();
