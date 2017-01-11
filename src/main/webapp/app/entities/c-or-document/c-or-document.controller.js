(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORDocumentController', CORDocumentController);

    CORDocumentController.$inject = ['$scope', '$state', 'DataUtils', 'CORDocument'];

    function CORDocumentController ($scope, $state, DataUtils, CORDocument) {
        var vm = this;

        vm.cORDocuments = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            CORDocument.query(function(result) {
                vm.cORDocuments = result;
                vm.searchQuery = null;
            });
        }
    }
})();
