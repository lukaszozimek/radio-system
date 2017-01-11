'use strict';

describe('Controller Tests', function() {

    describe('LIBMediaItem Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockLIBMediaItem, MockLIBLibrary, MockLIBLabel, MockLIBArtist, MockLIBAlbum, MockLIBTrack;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockLIBMediaItem = jasmine.createSpy('MockLIBMediaItem');
            MockLIBLibrary = jasmine.createSpy('MockLIBLibrary');
            MockLIBLabel = jasmine.createSpy('MockLIBLabel');
            MockLIBArtist = jasmine.createSpy('MockLIBArtist');
            MockLIBAlbum = jasmine.createSpy('MockLIBAlbum');
            MockLIBTrack = jasmine.createSpy('MockLIBTrack');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'LIBMediaItem': MockLIBMediaItem,
                'LIBLibrary': MockLIBLibrary,
                'LIBLabel': MockLIBLabel,
                'LIBArtist': MockLIBArtist,
                'LIBAlbum': MockLIBAlbum,
                'LIBTrack': MockLIBTrack
            };
            createController = function() {
                $injector.get('$controller')("LIBMediaItemDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'protoneApp:lIBMediaItemUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
