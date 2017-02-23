'use strict';

describe('Controller Tests', function() {

    describe('LibImageObject Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockLibImageObject, MockLibCloudObject, MockLibImageItem;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockLibImageObject = jasmine.createSpy('MockLibImageObject');
            MockLibCloudObject = jasmine.createSpy('MockLibCloudObject');
            MockLibImageItem = jasmine.createSpy('MockLibImageItem');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'LibImageObject': MockLibImageObject,
                'LibCloudObject': MockLibCloudObject,
                'LibImageItem': MockLibImageItem
            };
            createController = function() {
                $injector.get('$controller')("LibImageObjectDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'protoneApp:libImageObjectUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
