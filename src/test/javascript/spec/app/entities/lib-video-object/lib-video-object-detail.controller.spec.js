'use strict';

describe('Controller Tests', function() {

    describe('LibVideoObject Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockLibVideoObject, MockLibCloudObject, MockLibMediaItem;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockLibVideoObject = jasmine.createSpy('MockLibVideoObject');
            MockLibCloudObject = jasmine.createSpy('MockLibCloudObject');
            MockLibMediaItem = jasmine.createSpy('MockLibMediaItem');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'LibVideoObject': MockLibVideoObject,
                'LibCloudObject': MockLibCloudObject,
                'LibMediaItem': MockLibMediaItem
            };
            createController = function() {
                $injector.get('$controller')("LibVideoObjectDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'protoneApp:libVideoObjectUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
