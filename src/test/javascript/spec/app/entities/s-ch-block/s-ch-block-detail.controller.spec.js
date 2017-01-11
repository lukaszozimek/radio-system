'use strict';

describe('Controller Tests', function() {

    describe('SCHBlock Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockSCHBlock, MockCORChannel, MockSCHTemplate;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockSCHBlock = jasmine.createSpy('MockSCHBlock');
            MockCORChannel = jasmine.createSpy('MockCORChannel');
            MockSCHTemplate = jasmine.createSpy('MockSCHTemplate');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'SCHBlock': MockSCHBlock,
                'CORChannel': MockCORChannel,
                'SCHTemplate': MockSCHTemplate
            };
            createController = function() {
                $injector.get('$controller')("SCHBlockDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'protoneApp:sCHBlockUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
