(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraIndustryDetailController', TraIndustryDetailController);

    TraIndustryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TraIndustry', 'CorNetwork'];

    function TraIndustryDetailController($scope, $rootScope, $stateParams, previousState, entity, TraIndustry, CorNetwork) {
        var vm = this;

        vm.traIndustry = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:traIndustryUpdate', function(event, result) {
            vm.traIndustry = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
