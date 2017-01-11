(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORDocumentDialogController', CORDocumentDialogController);

    CORDocumentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'CORDocument', 'CORNetwork'];

    function CORDocumentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, CORDocument, CORNetwork) {
        var vm = this;

        vm.cORDocument = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.cornetworks = CORNetwork.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.cORDocument.id !== null) {
                CORDocument.update(vm.cORDocument, onSaveSuccess, onSaveError);
            } else {
                CORDocument.save(vm.cORDocument, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:cORDocumentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
