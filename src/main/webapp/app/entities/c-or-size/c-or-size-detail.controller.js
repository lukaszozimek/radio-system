(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORSizeDetailController', CORSizeDetailController);

    CORSizeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CORSize', 'CORNetwork'];

    function CORSizeDetailController($scope, $rootScope, $stateParams, previousState, entity, CORSize, CORNetwork) {
        var vm = this;

        vm.cORSize = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:cORSizeUpdate', function(event, result) {
            vm.cORSize = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
