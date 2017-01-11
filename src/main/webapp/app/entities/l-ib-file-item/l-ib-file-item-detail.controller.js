(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBFileItemDetailController', LIBFileItemDetailController);

    LIBFileItemDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LIBFileItem', 'LIBLibrary'];

    function LIBFileItemDetailController($scope, $rootScope, $stateParams, previousState, entity, LIBFileItem, LIBLibrary) {
        var vm = this;

        vm.lIBFileItem = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:lIBFileItemUpdate', function(event, result) {
            vm.lIBFileItem = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
