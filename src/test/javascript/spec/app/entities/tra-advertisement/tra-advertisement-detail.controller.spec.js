'use strict';

describe('Controller Tests', function() {

    describe('TraAdvertisement Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTraAdvertisement, MockLibMediaItem, MockTraIndustry, MockTraAdvertismentType, MockCrmAccount, MockCorNetwork;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTraAdvertisement = jasmine.createSpy('MockTraAdvertisement');
            MockLibMediaItem = jasmine.createSpy('MockLibMediaItem');
            MockTraIndustry = jasmine.createSpy('MockTraIndustry');
            MockTraAdvertismentType = jasmine.createSpy('MockTraAdvertismentType');
            MockCrmAccount = jasmine.createSpy('MockCrmAccount');
            MockCorNetwork = jasmine.createSpy('MockCorNetwork');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'TraAdvertisement': MockTraAdvertisement,
                'LibMediaItem': MockLibMediaItem,
                'TraIndustry': MockTraIndustry,
                'TraAdvertismentType': MockTraAdvertismentType,
                'CrmAccount': MockCrmAccount,
                'CorNetwork': MockCorNetwork
            };
            createController = function() {
                $injector.get('$controller')("TraAdvertisementDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'protoneApp:traAdvertisementUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
