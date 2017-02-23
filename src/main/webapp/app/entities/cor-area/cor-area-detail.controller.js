(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorAreaDetailController', CorAreaDetailController);

    CorAreaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CorArea', 'CorNetwork'];

    function CorAreaDetailController($scope, $rootScope, $stateParams, previousState, entity, CorArea, CorNetwork) {
        var vm = this;

        vm.corArea = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:corAreaUpdate', function(event, result) {
            vm.corArea = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
