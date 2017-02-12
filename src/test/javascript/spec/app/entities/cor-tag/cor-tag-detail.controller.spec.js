'use strict';

describe('Controller Tests', function() {

    describe('CorTag Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCorTag, MockCorNetwork, MockLibMediaItem;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCorTag = jasmine.createSpy('MockCorTag');
            MockCorNetwork = jasmine.createSpy('MockCorNetwork');
            MockLibMediaItem = jasmine.createSpy('MockLibMediaItem');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'CorTag': MockCorTag,
                'CorNetwork': MockCorNetwork,
                'LibMediaItem': MockLibMediaItem
            };
            createController = function() {
                $injector.get('$controller')("CorTagDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'protoneApp:corTagUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
