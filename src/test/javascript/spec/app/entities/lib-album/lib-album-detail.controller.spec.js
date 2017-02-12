'use strict';

describe('Controller Tests', function() {

    describe('LibAlbum Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockLibAlbum, MockLibImageItem, MockLibLabel, MockLibArtist, MockCorNetwork;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockLibAlbum = jasmine.createSpy('MockLibAlbum');
            MockLibImageItem = jasmine.createSpy('MockLibImageItem');
            MockLibLabel = jasmine.createSpy('MockLibLabel');
            MockLibArtist = jasmine.createSpy('MockLibArtist');
            MockCorNetwork = jasmine.createSpy('MockCorNetwork');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'LibAlbum': MockLibAlbum,
                'LibImageItem': MockLibImageItem,
                'LibLabel': MockLibLabel,
                'LibArtist': MockLibArtist,
                'CorNetwork': MockCorNetwork
            };
            createController = function() {
                $injector.get('$controller')("LibAlbumDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'protoneApp:libAlbumUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
