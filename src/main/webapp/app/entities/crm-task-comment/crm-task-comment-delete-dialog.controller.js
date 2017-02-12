(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmTaskCommentDeleteController',CrmTaskCommentDeleteController);

    CrmTaskCommentDeleteController.$inject = ['$uibModalInstance', 'entity', 'CrmTaskComment'];

    function CrmTaskCommentDeleteController($uibModalInstance, entity, CrmTaskComment) {
        var vm = this;

        vm.crmTaskComment = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CrmTaskComment.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
