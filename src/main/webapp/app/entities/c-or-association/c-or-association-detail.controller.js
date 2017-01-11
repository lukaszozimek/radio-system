(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORAssociationDetailController', CORAssociationDetailController);

    CORAssociationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CORAssociation', 'CORNetwork'];

    function CORAssociationDetailController($scope, $rootScope, $stateParams, previousState, entity, CORAssociation, CORNetwork) {
        var vm = this;

        vm.cORAssociation = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:cORAssociationUpdate', function(event, result) {
            vm.cORAssociation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
