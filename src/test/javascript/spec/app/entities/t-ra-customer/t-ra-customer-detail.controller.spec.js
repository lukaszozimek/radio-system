'use strict';

describe('Controller Tests', function() {

    describe('TRACustomer Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTRACustomer, MockTRAIndustry, MockCORNetwork, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTRACustomer = jasmine.createSpy('MockTRACustomer');
            MockTRAIndustry = jasmine.createSpy('MockTRAIndustry');
            MockCORNetwork = jasmine.createSpy('MockCORNetwork');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'TRACustomer': MockTRACustomer,
                'TRAIndustry': MockTRAIndustry,
                'CORNetwork': MockCORNetwork,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("TRACustomerDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'protoneApp:tRACustomerUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
