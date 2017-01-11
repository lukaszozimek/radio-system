(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBVideoObjectDialogController', LIBVideoObjectDialogController);

    LIBVideoObjectDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'LIBVideoObject', 'LIBCloudObject', 'LIBMediaItem'];

    function LIBVideoObjectDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, LIBVideoObject, LIBCloudObject, LIBMediaItem) {
        var vm = this;

        vm.lIBVideoObject = entity;
        vm.clear = clear;
        vm.save = save;
        vm.cloudobjects = LIBCloudObject.query({filter: 'libvideoobject-is-null'});
        $q.all([vm.lIBVideoObject.$promise, vm.cloudobjects.$promise]).then(function() {
            if (!vm.lIBVideoObject.cloudObjectId) {
                return $q.reject();
            }
            return LIBCloudObject.get({id : vm.lIBVideoObject.cloudObjectId}).$promise;
        }).then(function(cloudObject) {
            vm.cloudobjects.push(cloudObject);
        });
        vm.libmediaitems = LIBMediaItem.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.lIBVideoObject.id !== null) {
                LIBVideoObject.update(vm.lIBVideoObject, onSaveSuccess, onSaveError);
            } else {
                LIBVideoObject.save(vm.lIBVideoObject, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:lIBVideoObjectUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
