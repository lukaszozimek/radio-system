'use strict';

describe('Controller Tests', function() {

    describe('CrmContact Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCrmContact, MockCorAddress, MockCorCountry, MockCorPerson, MockCorNetwork, MockCorRange, MockCorSize, MockTraIndustry, MockCorArea, MockCorUser, MockCrmContactStatus, MockCrmTask;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCrmContact = jasmine.createSpy('MockCrmContact');
            MockCorAddress = jasmine.createSpy('MockCorAddress');
            MockCorCountry = jasmine.createSpy('MockCorCountry');
            MockCorPerson = jasmine.createSpy('MockCorPerson');
            MockCorNetwork = jasmine.createSpy('MockCorNetwork');
            MockCorRange = jasmine.createSpy('MockCorRange');
            MockCorSize = jasmine.createSpy('MockCorSize');
            MockTraIndustry = jasmine.createSpy('MockTraIndustry');
            MockCorArea = jasmine.createSpy('MockCorArea');
            MockCorUser = jasmine.createSpy('MockCorUser');
            MockCrmContactStatus = jasmine.createSpy('MockCrmContactStatus');
            MockCrmTask = jasmine.createSpy('MockCrmTask');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'CrmContact': MockCrmContact,
                'CorAddress': MockCorAddress,
                'CorCountry': MockCorCountry,
                'CorPerson': MockCorPerson,
                'CorNetwork': MockCorNetwork,
                'CorRange': MockCorRange,
                'CorSize': MockCorSize,
                'TraIndustry': MockTraIndustry,
                'CorArea': MockCorArea,
                'CorUser': MockCorUser,
                'CrmContactStatus': MockCrmContactStatus,
                'CrmTask': MockCrmTask
            };
            createController = function() {
                $injector.get('$controller')("CrmContactDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'protoneApp:crmContactUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
