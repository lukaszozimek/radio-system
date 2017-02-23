(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraCampaingStatusDetailController', TraCampaingStatusDetailController);

    TraCampaingStatusDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TraCampaingStatus'];

    function TraCampaingStatusDetailController($scope, $rootScope, $stateParams, previousState, entity, TraCampaingStatus) {
        var vm = this;

        vm.traCampaingStatus = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:traCampaingStatusUpdate', function(event, result) {
            vm.traCampaingStatus = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
