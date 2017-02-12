(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibAudioObjectDialogController', LibAudioObjectDialogController);

    LibAudioObjectDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'LibAudioObject', 'LibCloudObject', 'LibMediaItem'];

    function LibAudioObjectDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, LibAudioObject, LibCloudObject, LibMediaItem) {
        var vm = this;

        vm.libAudioObject = entity;
        vm.clear = clear;
        vm.save = save;
        vm.cloudobjects = LibCloudObject.query({filter: 'libaudioobject-is-null'});
        $q.all([vm.libAudioObject.$promise, vm.cloudobjects.$promise]).then(function() {
            if (!vm.libAudioObject.cloudObjectId) {
                return $q.reject();
            }
            return LibCloudObject.get({id : vm.libAudioObject.cloudObjectId}).$promise;
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
            if (vm.libAudioObject.id !== null) {
                LibAudioObject.update(vm.libAudioObject, onSaveSuccess, onSaveError);
            } else {
                LibAudioObject.save(vm.libAudioObject, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:libAudioObjectUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
