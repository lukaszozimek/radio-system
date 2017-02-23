(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmContactDialogController', CrmContactDialogController);

    CrmContactDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'CrmContact', 'CorAddress', 'CorCountry', 'CorPerson', 'CorNetwork', 'CorRange', 'CorSize', 'TraIndustry', 'CorArea', 'CorUser', 'CrmContactStatus', 'CrmTask'];

    function CrmContactDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, CrmContact, CorAddress, CorCountry, CorPerson, CorNetwork, CorRange, CorSize, TraIndustry, CorArea, CorUser, CrmContactStatus, CrmTask) {
        var vm = this;

        vm.crmContact = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.addres = CorAddress.query({filter: 'crmcontact-is-null'});
        $q.all([vm.crmContact.$promise, vm.addres.$promise]).then(function() {
            if (!vm.crmContact.addresId) {
                return $q.reject();
            }
            return CorAddress.get({id : vm.crmContact.addresId}).$promise;
        }).then(function(addres) {
            vm.addres.push(addres);
        });
        vm.countries = CorCountry.query({filter: 'crmcontact-is-null'});
        $q.all([vm.crmContact.$promise, vm.countries.$promise]).then(function() {
            if (!vm.crmContact.countryId) {
                return $q.reject();
            }
            return CorCountry.get({id : vm.crmContact.countryId}).$promise;
        }).then(function(country) {
            vm.countries.push(country);
        });
        vm.people = CorPerson.query({filter: 'crmcontact-is-null'});
        $q.all([vm.crmContact.$promise, vm.people.$promise]).then(function() {
            if (!vm.crmContact.personId) {
                return $q.reject();
            }
            return CorPerson.get({id : vm.crmContact.personId}).$promise;
        }).then(function(person) {
            vm.people.push(person);
        });
        vm.cornetworks = CorNetwork.query();
        vm.corranges = CorRange.query();
        vm.corsizes = CorSize.query();
        vm.traindustries = TraIndustry.query();
        vm.corareas = CorArea.query();
        vm.corusers = CorUser.query();
        vm.crmcontactstatuses = CrmContactStatus.query();
        vm.crmtasks = CrmTask.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.crmContact.id !== null) {
                CrmContact.update(vm.crmContact, onSaveSuccess, onSaveError);
            } else {
                CrmContact.save(vm.crmContact, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:crmContactUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.paymentDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
