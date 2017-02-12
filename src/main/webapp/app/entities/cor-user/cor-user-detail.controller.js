(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorUserDetailController', CorUserDetailController);

    CorUserDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CorUser', 'CorNetwork', 'CorChannel'];

    function CorUserDetailController($scope, $rootScope, $stateParams, previousState, entity, CorUser, CorNetwork, CorChannel) {
        var vm = this;

        vm.corUser = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:corUserUpdate', function(event, result) {
            vm.corUser = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
