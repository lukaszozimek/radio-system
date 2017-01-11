'use strict';

describe('Controller Tests', function() {

    describe('TRAAdvertisement Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTRAAdvertisement, MockLIBMediaItem, MockTRACustomer, MockTRAIndustry;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTRAAdvertisement = jasmine.createSpy('MockTRAAdvertisement');
            MockLIBMediaItem = jasmine.createSpy('MockLIBMediaItem');
            MockTRACustomer = jasmine.createSpy('MockTRACustomer');
            MockTRAIndustry = jasmine.createSpy('MockTRAIndustry');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'TRAAdvertisement': MockTRAAdvertisement,
                'LIBMediaItem': MockLIBMediaItem,
                'TRACustomer': MockTRACustomer,
                'TRAIndustry': MockTRAIndustry
            };
            createController = function() {
                $injector.get('$controller')("TRAAdvertisementDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'protoneApp:tRAAdvertisementUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
