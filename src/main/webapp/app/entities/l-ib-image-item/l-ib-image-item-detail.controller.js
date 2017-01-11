(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBImageItemDetailController', LIBImageItemDetailController);

    LIBImageItemDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LIBImageItem', 'LIBLibrary'];

    function LIBImageItemDetailController($scope, $rootScope, $stateParams, previousState, entity, LIBImageItem, LIBLibrary) {
        var vm = this;

        vm.lIBImageItem = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:lIBImageItemUpdate', function(event, result) {
            vm.lIBImageItem = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
