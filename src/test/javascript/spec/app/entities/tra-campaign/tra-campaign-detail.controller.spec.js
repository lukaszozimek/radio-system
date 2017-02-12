'use strict';

describe('Controller Tests', function() {

    describe('TraCampaign Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTraCampaign, MockCrmAccount, MockCorNetwork, MockTraCampaingStatus, MockTraPrice, MockTraOrder, MockSchEmission;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTraCampaign = jasmine.createSpy('MockTraCampaign');
            MockCrmAccount = jasmine.createSpy('MockCrmAccount');
            MockCorNetwork = jasmine.createSpy('MockCorNetwork');
            MockTraCampaingStatus = jasmine.createSpy('MockTraCampaingStatus');
            MockTraPrice = jasmine.createSpy('MockTraPrice');
            MockTraOrder = jasmine.createSpy('MockTraOrder');
            MockSchEmission = jasmine.createSpy('MockSchEmission');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'TraCampaign': MockTraCampaign,
                'CrmAccount': MockCrmAccount,
                'CorNetwork': MockCorNetwork,
                'TraCampaingStatus': MockTraCampaingStatus,
                'TraPrice': MockTraPrice,
                'TraOrder': MockTraOrder,
                'SchEmission': MockSchEmission
            };
            createController = function() {
                $injector.get('$controller')("TraCampaignDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'protoneApp:traCampaignUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
