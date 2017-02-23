(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorContactDetailController', CorContactDetailController);

    CorContactDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CorContact', 'CorNetwork', 'CorPerson'];

    function CorContactDetailController($scope, $rootScope, $stateParams, previousState, entity, CorContact, CorNetwork, CorPerson) {
        var vm = this;

        vm.corContact = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:corContactUpdate', function(event, result) {
            vm.corContact = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
