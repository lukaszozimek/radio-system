(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('SchTemplateDetailController', SchTemplateDetailController);

    SchTemplateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SchTemplate', 'CorChannel'];

    function SchTemplateDetailController($scope, $rootScope, $stateParams, previousState, entity, SchTemplate, CorChannel) {
        var vm = this;

        vm.schTemplate = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:schTemplateUpdate', function(event, result) {
            vm.schTemplate = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
