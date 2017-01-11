(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORPropertyKeyDetailController', CORPropertyKeyDetailController);

    CORPropertyKeyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CORPropertyKey', 'CORNetwork'];

    function CORPropertyKeyDetailController($scope, $rootScope, $stateParams, previousState, entity, CORPropertyKey, CORNetwork) {
        var vm = this;

        vm.cORPropertyKey = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:cORPropertyKeyUpdate', function(event, result) {
            vm.cORPropertyKey = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
