(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TRAIndustryDetailController', TRAIndustryDetailController);

    TRAIndustryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TRAIndustry', 'CORNetwork'];

    function TRAIndustryDetailController($scope, $rootScope, $stateParams, previousState, entity, TRAIndustry, CORNetwork) {
        var vm = this;

        vm.tRAIndustry = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:tRAIndustryUpdate', function(event, result) {
            vm.tRAIndustry = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
