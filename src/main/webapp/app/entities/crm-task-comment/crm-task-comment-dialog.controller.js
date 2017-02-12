(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmTaskCommentDialogController', CrmTaskCommentDialogController);

    CrmTaskCommentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CrmTaskComment', 'CrmTask', 'CorUser'];

    function CrmTaskCommentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CrmTaskComment, CrmTask, CorUser) {
        var vm = this;

        vm.crmTaskComment = entity;
        vm.clear = clear;
        vm.save = save;
        vm.crmtasks = CrmTask.query();
        vm.corusers = CorUser.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.crmTaskComment.id !== null) {
                CrmTaskComment.update(vm.crmTaskComment, onSaveSuccess, onSaveError);
            } else {
                CrmTaskComment.save(vm.crmTaskComment, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:crmTaskCommentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
