(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('SchTemplateDialogController', SchTemplateDialogController);

    SchTemplateDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SchTemplate', 'CorChannel'];

    function SchTemplateDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SchTemplate, CorChannel) {
        var vm = this;

        vm.schTemplate = entity;
        vm.clear = clear;
        vm.save = save;
        vm.corchannels = CorChannel.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.schTemplate.id !== null) {
                SchTemplate.update(vm.schTemplate, onSaveSuccess, onSaveError);
            } else {
                SchTemplate.save(vm.schTemplate, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:schTemplateUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
