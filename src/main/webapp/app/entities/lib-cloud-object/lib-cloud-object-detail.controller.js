(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibCloudObjectDetailController', LibCloudObjectDetailController);

    LibCloudObjectDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LibCloudObject', 'CorNetwork', 'CorUser'];

    function LibCloudObjectDetailController($scope, $rootScope, $stateParams, previousState, entity, LibCloudObject, CorNetwork, CorUser) {
        var vm = this;

        vm.libCloudObject = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:libCloudObjectUpdate', function(event, result) {
            vm.libCloudObject = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
