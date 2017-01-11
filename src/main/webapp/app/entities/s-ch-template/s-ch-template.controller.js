(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('SCHTemplateController', SCHTemplateController);

    SCHTemplateController.$inject = ['$scope', '$state', 'SCHTemplate'];

    function SCHTemplateController ($scope, $state, SCHTemplate) {
        var vm = this;

        vm.sCHTemplates = [];

        loadAll();

        function loadAll() {
            SCHTemplate.query(function(result) {
                vm.sCHTemplates = result;
                vm.searchQuery = null;
            });
        }
    }
})();
