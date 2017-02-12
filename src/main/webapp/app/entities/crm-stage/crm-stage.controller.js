(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmStageController', CrmStageController);

    CrmStageController.$inject = ['$scope', '$state', 'CrmStage'];

    function CrmStageController ($scope, $state, CrmStage) {
        var vm = this;

        vm.crmStages = [];

        loadAll();

        function loadAll() {
            CrmStage.query(function(result) {
                vm.crmStages = result;
                vm.searchQuery = null;
            });
        }
    }
})();
