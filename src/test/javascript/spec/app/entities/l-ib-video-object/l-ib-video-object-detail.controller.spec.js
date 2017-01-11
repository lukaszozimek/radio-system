'use strict';

describe('Controller Tests', function() {

    describe('LIBVideoObject Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockLIBVideoObject, MockLIBCloudObject, MockLIBMediaItem;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockLIBVideoObject = jasmine.createSpy('MockLIBVideoObject');
            MockLIBCloudObject = jasmine.createSpy('MockLIBCloudObject');
            MockLIBMediaItem = jasmine.createSpy('MockLIBMediaItem');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'LIBVideoObject': MockLIBVideoObject,
                'LIBCloudObject': MockLIBCloudObject,
                'LIBMediaItem': MockLIBMediaItem
            };
            createController = function() {
                $injector.get('$controller')("LIBVideoObjectDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'protoneApp:lIBVideoObjectUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
