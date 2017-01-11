(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORContactDetailController', CORContactDetailController);

    CORContactDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CORContact', 'CORNetwork'];

    function CORContactDetailController($scope, $rootScope, $stateParams, previousState, entity, CORContact, CORNetwork) {
        var vm = this;

        vm.cORContact = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:cORContactUpdate', function(event, result) {
            vm.cORContact = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
