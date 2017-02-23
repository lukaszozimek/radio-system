'use strict';

describe('Controller Tests', function() {

    describe('CrmTask Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCrmTask, MockCorUser, MockCrmTaskStatus, MockCorNetwork, MockCrmOpportunity, MockCrmContact, MockCrmAccount, MockCrmLead;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCrmTask = jasmine.createSpy('MockCrmTask');
            MockCorUser = jasmine.createSpy('MockCorUser');
            MockCrmTaskStatus = jasmine.createSpy('MockCrmTaskStatus');
            MockCorNetwork = jasmine.createSpy('MockCorNetwork');
            MockCrmOpportunity = jasmine.createSpy('MockCrmOpportunity');
            MockCrmContact = jasmine.createSpy('MockCrmContact');
            MockCrmAccount = jasmine.createSpy('MockCrmAccount');
            MockCrmLead = jasmine.createSpy('MockCrmLead');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'CrmTask': MockCrmTask,
                'CorUser': MockCorUser,
                'CrmTaskStatus': MockCrmTaskStatus,
                'CorNetwork': MockCorNetwork,
                'CrmOpportunity': MockCrmOpportunity,
                'CrmContact': MockCrmContact,
                'CrmAccount': MockCrmAccount,
                'CrmLead': MockCrmLead
            };
            createController = function() {
                $injector.get('$controller')("CrmTaskDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'protoneApp:crmTaskUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
