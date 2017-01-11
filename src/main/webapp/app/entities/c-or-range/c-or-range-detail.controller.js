(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORRangeDetailController', CORRangeDetailController);

    CORRangeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CORRange', 'CORNetwork'];

    function CORRangeDetailController($scope, $rootScope, $stateParams, previousState, entity, CORRange, CORNetwork) {
        var vm = this;

        vm.cORRange = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:cORRangeUpdate', function(event, result) {
            vm.cORRange = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
