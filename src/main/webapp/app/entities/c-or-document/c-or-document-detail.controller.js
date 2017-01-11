(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORDocumentDetailController', CORDocumentDetailController);

    CORDocumentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'CORDocument', 'CORNetwork'];

    function CORDocumentDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, CORDocument, CORNetwork) {
        var vm = this;

        vm.cORDocument = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('protoneApp:cORDocumentUpdate', function(event, result) {
            vm.cORDocument = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
