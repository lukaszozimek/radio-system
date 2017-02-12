(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CfgMarkerConfigurationDetailController', CfgMarkerConfigurationDetailController);

    CfgMarkerConfigurationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CfgMarkerConfiguration', 'CorNetwork'];

    function CfgMarkerConfigurationDetailController($scope, $rootScope, $stateParams, previousState, entity, CfgMarkerConfiguration, CorNetwork) {
        var vm = this;

        vm.cfgMarkerConfiguration = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:cfgMarkerConfigurationUpdate', function(event, result) {
            vm.cfgMarkerConfiguration = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
