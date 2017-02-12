'use strict';

describe('Controller Tests', function() {

    describe('CrmLead Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCrmLead, MockCorPerson, MockCorAddress, MockCrmLeadStatus, MockCrmLeadSource, MockCorUser, MockTraIndustry, MockCorArea, MockCorNetwork, MockCrmTask;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCrmLead = jasmine.createSpy('MockCrmLead');
            MockCorPerson = jasmine.createSpy('MockCorPerson');
            MockCorAddress = jasmine.createSpy('MockCorAddress');
            MockCrmLeadStatus = jasmine.createSpy('MockCrmLeadStatus');
            MockCrmLeadSource = jasmine.createSpy('MockCrmLeadSource');
            MockCorUser = jasmine.createSpy('MockCorUser');
            MockTraIndustry = jasmine.createSpy('MockTraIndustry');
            MockCorArea = jasmine.createSpy('MockCorArea');
            MockCorNetwork = jasmine.createSpy('MockCorNetwork');
            MockCrmTask = jasmine.createSpy('MockCrmTask');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'CrmLead': MockCrmLead,
                'CorPerson': MockCorPerson,
                'CorAddress': MockCorAddress,
                'CrmLeadStatus': MockCrmLeadStatus,
                'CrmLeadSource': MockCrmLeadSource,
                'CorUser': MockCorUser,
                'TraIndustry': MockTraIndustry,
                'CorArea': MockCorArea,
                'CorNetwork': MockCorNetwork,
                'CrmTask': MockCrmTask
            };
            createController = function() {
                $injector.get('$controller')("CrmLeadDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'protoneApp:crmLeadUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
