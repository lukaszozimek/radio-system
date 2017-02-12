(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmLeadDialogController', CrmLeadDialogController);

    CrmLeadDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'CrmLead', 'CorPerson', 'CorAddress', 'CrmLeadStatus', 'CrmLeadSource', 'CorUser', 'TraIndustry', 'CorArea', 'CorNetwork', 'CrmTask'];

    function CrmLeadDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, CrmLead, CorPerson, CorAddress, CrmLeadStatus, CrmLeadSource, CorUser, TraIndustry, CorArea, CorNetwork, CrmTask) {
        var vm = this;

        vm.crmLead = entity;
        vm.clear = clear;
        vm.save = save;
        vm.people = CorPerson.query({filter: 'crmlead-is-null'});
        $q.all([vm.crmLead.$promise, vm.people.$promise]).then(function() {
            if (!vm.crmLead.personId) {
                return $q.reject();
            }
            return CorPerson.get({id : vm.crmLead.personId}).$promise;
        }).then(function(person) {
            vm.people.push(person);
        });
        vm.addres = CorAddress.query({filter: 'crmlead-is-null'});
        $q.all([vm.crmLead.$promise, vm.addres.$promise]).then(function() {
            if (!vm.crmLead.addresId) {
                return $q.reject();
            }
            return CorAddress.get({id : vm.crmLead.addresId}).$promise;
        }).then(function(addres) {
            vm.addres.push(addres);
        });
        vm.crmleadstatuses = CrmLeadStatus.query();
        vm.crmleadsources = CrmLeadSource.query();
        vm.corusers = CorUser.query();
        vm.traindustries = TraIndustry.query();
        vm.corareas = CorArea.query();
        vm.cornetworks = CorNetwork.query();
        vm.crmtasks = CrmTask.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.crmLead.id !== null) {
                CrmLead.update(vm.crmLead, onSaveSuccess, onSaveError);
            } else {
                CrmLead.save(vm.crmLead, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:crmLeadUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
