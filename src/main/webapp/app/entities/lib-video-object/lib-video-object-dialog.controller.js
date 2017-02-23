(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibVideoObjectDialogController', LibVideoObjectDialogController);

    LibVideoObjectDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'LibVideoObject', 'LibCloudObject', 'LibMediaItem'];

    function LibVideoObjectDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, LibVideoObject, LibCloudObject, LibMediaItem) {
        var vm = this;

        vm.libVideoObject = entity;
        vm.clear = clear;
        vm.save = save;
        vm.cloudobjects = LibCloudObject.query({filter: 'libvideoobject-is-null'});
        $q.all([vm.libVideoObject.$promise, vm.cloudobjects.$promise]).then(function() {
            if (!vm.libVideoObject.cloudObjectId) {
                return $q.reject();
            }
            return LibCloudObject.get({id : vm.libVideoObject.cloudObjectId}).$promise;
        }).then(function(cloudObject) {
            vm.cloudobjects.push(cloudObject);
        });
        vm.libmediaitems = LibMediaItem.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.libVideoObject.id !== null) {
                LibVideoObject.update(vm.libVideoObject, onSaveSuccess, onSaveError);
            } else {
                LibVideoObject.save(vm.libVideoObject, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:libVideoObjectUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
