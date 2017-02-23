(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraAdvertismentTypeDetailController', TraAdvertismentTypeDetailController);

    TraAdvertismentTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TraAdvertismentType'];

    function TraAdvertismentTypeDetailController($scope, $rootScope, $stateParams, previousState, entity, TraAdvertismentType) {
        var vm = this;

        vm.traAdvertismentType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:traAdvertismentTypeUpdate', function(event, result) {
            vm.traAdvertismentType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
