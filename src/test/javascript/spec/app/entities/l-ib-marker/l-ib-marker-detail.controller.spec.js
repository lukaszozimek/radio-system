'use strict';

describe('Controller Tests', function() {

    describe('LIBMarker Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockLIBMarker, MockLIBMediaItem;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockLIBMarker = jasmine.createSpy('MockLIBMarker');
            MockLIBMediaItem = jasmine.createSpy('MockLIBMediaItem');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'LIBMarker': MockLIBMarker,
                'LIBMediaItem': MockLIBMediaItem
            };
            createController = function() {
                $injector.get('$controller')("LIBMarkerDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'protoneApp:lIBMarkerUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
