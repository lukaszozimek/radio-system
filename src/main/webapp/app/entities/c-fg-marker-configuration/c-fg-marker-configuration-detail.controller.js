(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CFGMarkerConfigurationDetailController', CFGMarkerConfigurationDetailController);

    CFGMarkerConfigurationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CFGMarkerConfiguration', 'CORNetwork'];

    function CFGMarkerConfigurationDetailController($scope, $rootScope, $stateParams, previousState, entity, CFGMarkerConfiguration, CORNetwork) {
        var vm = this;

        vm.cFGMarkerConfiguration = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:cFGMarkerConfigurationUpdate', function(event, result) {
            vm.cFGMarkerConfiguration = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
