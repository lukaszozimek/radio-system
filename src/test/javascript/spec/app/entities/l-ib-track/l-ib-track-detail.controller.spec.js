'use strict';

describe('Controller Tests', function() {

    describe('LIBTrack Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockLIBTrack, MockLIBAlbum, MockLIBArtist, MockCORNetwork;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockLIBTrack = jasmine.createSpy('MockLIBTrack');
            MockLIBAlbum = jasmine.createSpy('MockLIBAlbum');
            MockLIBArtist = jasmine.createSpy('MockLIBArtist');
            MockCORNetwork = jasmine.createSpy('MockCORNetwork');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'LIBTrack': MockLIBTrack,
                'LIBAlbum': MockLIBAlbum,
                'LIBArtist': MockLIBArtist,
                'CORNetwork': MockCORNetwork
            };
            createController = function() {
                $injector.get('$controller')("LIBTrackDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'protoneApp:lIBTrackUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
