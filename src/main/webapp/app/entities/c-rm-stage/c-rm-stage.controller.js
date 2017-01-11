(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CRMStageController', CRMStageController);

    CRMStageController.$inject = ['$scope', '$state', 'CRMStage'];

    function CRMStageController ($scope, $state, CRMStage) {
        var vm = this;

        vm.cRMStages = [];

        loadAll();

        function loadAll() {
            CRMStage.query(function(result) {
                vm.cRMStages = result;
                vm.searchQuery = null;
            });
        }
    }
})();
