(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmTaskCommentDetailController', CrmTaskCommentDetailController);

    CrmTaskCommentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CrmTaskComment', 'CrmTask', 'CorUser'];

    function CrmTaskCommentDetailController($scope, $rootScope, $stateParams, previousState, entity, CrmTaskComment, CrmTask, CorUser) {
        var vm = this;

        vm.crmTaskComment = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:crmTaskCommentUpdate', function(event, result) {
            vm.crmTaskComment = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
