(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORAreaDetailController', CORAreaDetailController);

    CORAreaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CORArea', 'CORNetwork'];

    function CORAreaDetailController($scope, $rootScope, $stateParams, previousState, entity, CORArea, CORNetwork) {
        var vm = this;

        vm.cORArea = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:cORAreaUpdate', function(event, result) {
            vm.cORArea = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
