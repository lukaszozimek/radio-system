(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('SCHTemplateDetailController', SCHTemplateDetailController);

    SCHTemplateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SCHTemplate', 'CORChannel'];

    function SCHTemplateDetailController($scope, $rootScope, $stateParams, previousState, entity, SCHTemplate, CORChannel) {
        var vm = this;

        vm.sCHTemplate = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:sCHTemplateUpdate', function(event, result) {
            vm.sCHTemplate = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
