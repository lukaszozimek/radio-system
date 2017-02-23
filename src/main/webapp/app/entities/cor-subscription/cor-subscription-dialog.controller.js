(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorSubscriptionDialogController', CorSubscriptionDialogController);

    CorSubscriptionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CorSubscription'];

    function CorSubscriptionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CorSubscription) {
        var vm = this;

        vm.corSubscription = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.corSubscription.id !== null) {
                CorSubscription.update(vm.corSubscription, onSaveSuccess, onSaveError);
            } else {
                CorSubscription.save(vm.corSubscription, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:corSubscriptionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
