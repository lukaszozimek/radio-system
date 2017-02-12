(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorCountryDetailController', CorCountryDetailController);

    CorCountryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CorCountry', 'CorTax', 'CorCurrency', 'CorNetwork'];

    function CorCountryDetailController($scope, $rootScope, $stateParams, previousState, entity, CorCountry, CorTax, CorCurrency, CorNetwork) {
        var vm = this;

        vm.corCountry = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:corCountryUpdate', function(event, result) {
            vm.corCountry = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
