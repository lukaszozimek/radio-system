(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmTaskCommentController', CrmTaskCommentController);

    CrmTaskCommentController.$inject = ['$scope', '$state', 'CrmTaskComment'];

    function CrmTaskCommentController ($scope, $state, CrmTaskComment) {
        var vm = this;

        vm.crmTaskComments = [];

        loadAll();

        function loadAll() {
            CrmTaskComment.query(function(result) {
                vm.crmTaskComments = result;
                vm.searchQuery = null;
            });
        }
    }
})();
