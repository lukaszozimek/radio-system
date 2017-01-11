'use strict';

describe('Controller Tests', function() {

    describe('SCHEmission Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockSCHEmission, MockSCHBlock, MockLIBMediaItem, MockSCHTemplate;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockSCHEmission = jasmine.createSpy('MockSCHEmission');
            MockSCHBlock = jasmine.createSpy('MockSCHBlock');
            MockLIBMediaItem = jasmine.createSpy('MockLIBMediaItem');
            MockSCHTemplate = jasmine.createSpy('MockSCHTemplate');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'SCHEmission': MockSCHEmission,
                'SCHBlock': MockSCHBlock,
                'LIBMediaItem': MockLIBMediaItem,
                'SCHTemplate': MockSCHTemplate
            };
            createController = function() {
                $injector.get('$controller')("SCHEmissionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'protoneApp:sCHEmissionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
