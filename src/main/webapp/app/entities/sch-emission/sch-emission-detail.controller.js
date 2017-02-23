(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('SchEmissionDetailController', SchEmissionDetailController);

    SchEmissionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SchEmission', 'LibMediaItem', 'SchBlock', 'TraCampaign'];

    function SchEmissionDetailController($scope, $rootScope, $stateParams, previousState, entity, SchEmission, LibMediaItem, SchBlock, TraCampaign) {
        var vm = this;

        vm.schEmission = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:schEmissionUpdate', function(event, result) {
            vm.schEmission = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
