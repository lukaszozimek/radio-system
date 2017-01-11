'use strict';

describe('Controller Tests', function() {

    describe('LIBAlbum Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockLIBAlbum, MockLIBImageItem, MockLIBLabel, MockLIBArtist, MockCORNetwork;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockLIBAlbum = jasmine.createSpy('MockLIBAlbum');
            MockLIBImageItem = jasmine.createSpy('MockLIBImageItem');
            MockLIBLabel = jasmine.createSpy('MockLIBLabel');
            MockLIBArtist = jasmine.createSpy('MockLIBArtist');
            MockCORNetwork = jasmine.createSpy('MockCORNetwork');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'LIBAlbum': MockLIBAlbum,
                'LIBImageItem': MockLIBImageItem,
                'LIBLabel': MockLIBLabel,
                'LIBArtist': MockLIBArtist,
                'CORNetwork': MockCORNetwork
            };
            createController = function() {
                $injector.get('$controller')("LIBAlbumDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'protoneApp:lIBAlbumUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
