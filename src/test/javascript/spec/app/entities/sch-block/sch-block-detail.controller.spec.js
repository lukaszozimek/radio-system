'use strict';

describe('Controller Tests', function() {

    describe('SchBlock Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockSchBlock, MockSchPlaylist, MockSchTemplate;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockSchBlock = jasmine.createSpy('MockSchBlock');
            MockSchPlaylist = jasmine.createSpy('MockSchPlaylist');
            MockSchTemplate = jasmine.createSpy('MockSchTemplate');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'SchBlock': MockSchBlock,
                'SchPlaylist': MockSchPlaylist,
                'SchTemplate': MockSchTemplate
            };
            createController = function() {
                $injector.get('$controller')("SchBlockDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'protoneApp:schBlockUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
