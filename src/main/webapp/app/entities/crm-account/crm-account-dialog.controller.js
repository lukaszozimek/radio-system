(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmAccountDialogController', CrmAccountDialogController);

    CrmAccountDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'CrmAccount', 'CorPerson', 'CorAddress', 'CorNetwork', 'TraDiscount', 'CorUser', 'CorCountry', 'CorRange', 'CorSize', 'TraIndustry', 'CorArea', 'CrmTask'];

    function CrmAccountDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, CrmAccount, CorPerson, CorAddress, CorNetwork, TraDiscount, CorUser, CorCountry, CorRange, CorSize, TraIndustry, CorArea, CrmTask) {
        var vm = this;

        vm.crmAccount = entity;
        vm.clear = clear;
        vm.save = save;
        vm.people = CorPerson.query({filter: 'crmaccount-is-null'});
        $q.all([vm.crmAccount.$promise, vm.people.$promise]).then(function() {
            if (!vm.crmAccount.personId) {
                return $q.reject();
            }
            return CorPerson.get({id : vm.crmAccount.personId}).$promise;
        }).then(function(person) {
            vm.people.push(person);
        });
        vm.addres = CorAddress.query({filter: 'crmaccount-is-null'});
        $q.all([vm.crmAccount.$promise, vm.addres.$promise]).then(function() {
            if (!vm.crmAccount.addresId) {
                return $q.reject();
            }
            return CorAddress.get({id : vm.crmAccount.addresId}).$promise;
        }).then(function(addres) {
            vm.addres.push(addres);
        });
        vm.cornetworks = CorNetwork.query();
        vm.tradiscounts = TraDiscount.query();
        vm.corusers = CorUser.query();
        vm.corcountries = CorCountry.query();
        vm.corranges = CorRange.query();
        vm.corsizes = CorSize.query();
        vm.traindustries = TraIndustry.query();
        vm.corareas = CorArea.query();
        vm.crmtasks = CrmTask.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.crmAccount.id !== null) {
                CrmAccount.update(vm.crmAccount, onSaveSuccess, onSaveError);
            } else {
                CrmAccount.save(vm.crmAccount, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:crmAccountUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
