(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORPersonDetailController', CORPersonDetailController);

    CORPersonDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CORPerson', 'CORNetwork'];

    function CORPersonDetailController($scope, $rootScope, $stateParams, previousState, entity, CORPerson, CORNetwork) {
        var vm = this;

        vm.cORPerson = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:cORPersonUpdate', function(event, result) {
            vm.cORPerson = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
