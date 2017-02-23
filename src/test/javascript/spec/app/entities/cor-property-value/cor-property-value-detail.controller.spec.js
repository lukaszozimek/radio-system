'use strict';

describe('Controller Tests', function() {

    describe('CorPropertyValue Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCorPropertyValue, MockCorPropertyKey, MockLibMediaItem;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCorPropertyValue = jasmine.createSpy('MockCorPropertyValue');
            MockCorPropertyKey = jasmine.createSpy('MockCorPropertyKey');
            MockLibMediaItem = jasmine.createSpy('MockLibMediaItem');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'CorPropertyValue': MockCorPropertyValue,
                'CorPropertyKey': MockCorPropertyKey,
                'LibMediaItem': MockLibMediaItem
            };
            createController = function() {
                $injector.get('$controller')("CorPropertyValueDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'protoneApp:corPropertyValueUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
