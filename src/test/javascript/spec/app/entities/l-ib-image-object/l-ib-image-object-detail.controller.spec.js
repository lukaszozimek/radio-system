'use strict';

describe('Controller Tests', function() {

    describe('LIBImageObject Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockLIBImageObject, MockLIBCloudObject, MockLIBImageItem;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockLIBImageObject = jasmine.createSpy('MockLIBImageObject');
            MockLIBCloudObject = jasmine.createSpy('MockLIBCloudObject');
            MockLIBImageItem = jasmine.createSpy('MockLIBImageItem');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'LIBImageObject': MockLIBImageObject,
                'LIBCloudObject': MockLIBCloudObject,
                'LIBImageItem': MockLIBImageItem
            };
            createController = function() {
                $injector.get('$controller')("LIBImageObjectDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'protoneApp:lIBImageObjectUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
