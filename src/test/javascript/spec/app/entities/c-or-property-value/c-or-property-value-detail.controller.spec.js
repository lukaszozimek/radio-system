'use strict';

describe('Controller Tests', function() {

    describe('CORPropertyValue Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCORPropertyValue, MockCORPropertyKey;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCORPropertyValue = jasmine.createSpy('MockCORPropertyValue');
            MockCORPropertyKey = jasmine.createSpy('MockCORPropertyKey');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'CORPropertyValue': MockCORPropertyValue,
                'CORPropertyKey': MockCORPropertyKey
            };
            createController = function() {
                $injector.get('$controller')("CORPropertyValueDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'protoneApp:cORPropertyValueUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
