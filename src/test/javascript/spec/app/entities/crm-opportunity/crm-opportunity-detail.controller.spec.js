'use strict';

describe('Controller Tests', function() {

    describe('CrmOpportunity Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCrmOpportunity, MockCrmStage, MockCorUser, MockCrmContact, MockCrmAccount, MockCrmLead, MockCorNetwork, MockCrmTask;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCrmOpportunity = jasmine.createSpy('MockCrmOpportunity');
            MockCrmStage = jasmine.createSpy('MockCrmStage');
            MockCorUser = jasmine.createSpy('MockCorUser');
            MockCrmContact = jasmine.createSpy('MockCrmContact');
            MockCrmAccount = jasmine.createSpy('MockCrmAccount');
            MockCrmLead = jasmine.createSpy('MockCrmLead');
            MockCorNetwork = jasmine.createSpy('MockCorNetwork');
            MockCrmTask = jasmine.createSpy('MockCrmTask');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'CrmOpportunity': MockCrmOpportunity,
                'CrmStage': MockCrmStage,
                'CorUser': MockCorUser,
                'CrmContact': MockCrmContact,
                'CrmAccount': MockCrmAccount,
                'CrmLead': MockCrmLead,
                'CorNetwork': MockCorNetwork,
                'CrmTask': MockCrmTask
            };
            createController = function() {
                $injector.get('$controller')("CrmOpportunityDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'protoneApp:crmOpportunityUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
