(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORAssociationController', CORAssociationController);

    CORAssociationController.$inject = ['$scope', '$state', 'CORAssociation'];

    function CORAssociationController ($scope, $state, CORAssociation) {
        var vm = this;

        vm.cORAssociations = [];

        loadAll();

        function loadAll() {
            CORAssociation.query(function(result) {
                vm.cORAssociations = result;
                vm.searchQuery = null;
            });
        }
    }
})();
