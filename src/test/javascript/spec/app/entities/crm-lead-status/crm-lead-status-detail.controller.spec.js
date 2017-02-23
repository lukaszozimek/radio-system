'use strict';

describe('Controller Tests', function() {

    describe('CrmLeadStatus Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCrmLeadStatus, MockCorNetwork;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCrmLeadStatus = jasmine.createSpy('MockCrmLeadStatus');
            MockCorNetwork = jasmine.createSpy('MockCorNetwork');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'CrmLeadStatus': MockCrmLeadStatus,
                'CorNetwork': MockCorNetwork
            };
            createController = function() {
                $injector.get('$controller')("CrmLeadStatusDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'protoneApp:crmLeadStatusUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
