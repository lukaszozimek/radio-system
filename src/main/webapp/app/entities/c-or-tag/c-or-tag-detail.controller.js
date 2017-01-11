(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORTagDetailController', CORTagDetailController);

    CORTagDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CORTag', 'CORNetwork'];

    function CORTagDetailController($scope, $rootScope, $stateParams, previousState, entity, CORTag, CORNetwork) {
        var vm = this;

        vm.cORTag = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:cORTagUpdate', function(event, result) {
            vm.cORTag = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
