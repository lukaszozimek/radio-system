'use strict';

describe('Controller Tests', function() {

    describe('LibTrack Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockLibTrack, MockLibAlbum, MockLibArtist, MockCorNetwork;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockLibTrack = jasmine.createSpy('MockLibTrack');
            MockLibAlbum = jasmine.createSpy('MockLibAlbum');
            MockLibArtist = jasmine.createSpy('MockLibArtist');
            MockCorNetwork = jasmine.createSpy('MockCorNetwork');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'LibTrack': MockLibTrack,
                'LibAlbum': MockLibAlbum,
                'LibArtist': MockLibArtist,
                'CorNetwork': MockCorNetwork
            };
            createController = function() {
                $injector.get('$controller')("LibTrackDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'protoneApp:libTrackUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
