'use strict';

describe('Controller Tests', function() {

    describe('LibMediaItem Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockLibMediaItem, MockLibLibrary, MockLibLabel, MockLibArtist, MockLibAlbum, MockLibTrack;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockLibMediaItem = jasmine.createSpy('MockLibMediaItem');
            MockLibLibrary = jasmine.createSpy('MockLibLibrary');
            MockLibLabel = jasmine.createSpy('MockLibLabel');
            MockLibArtist = jasmine.createSpy('MockLibArtist');
            MockLibAlbum = jasmine.createSpy('MockLibAlbum');
            MockLibTrack = jasmine.createSpy('MockLibTrack');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'LibMediaItem': MockLibMediaItem,
                'LibLibrary': MockLibLibrary,
                'LibLabel': MockLibLabel,
                'LibArtist': MockLibArtist,
                'LibAlbum': MockLibAlbum,
                'LibTrack': MockLibTrack
            };
            createController = function() {
                $injector.get('$controller')("LibMediaItemDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'protoneApp:libMediaItemUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
