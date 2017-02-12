'use strict';

describe('Controller Tests', function() {

    describe('TraOrder Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTraOrder, MockCrmAccount, MockTraCampaign, MockTraPrice, MockCorNetwork, MockTraOrderStatus, MockTraInvoice;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTraOrder = jasmine.createSpy('MockTraOrder');
            MockCrmAccount = jasmine.createSpy('MockCrmAccount');
            MockTraCampaign = jasmine.createSpy('MockTraCampaign');
            MockTraPrice = jasmine.createSpy('MockTraPrice');
            MockCorNetwork = jasmine.createSpy('MockCorNetwork');
            MockTraOrderStatus = jasmine.createSpy('MockTraOrderStatus');
            MockTraInvoice = jasmine.createSpy('MockTraInvoice');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'TraOrder': MockTraOrder,
                'CrmAccount': MockCrmAccount,
                'TraCampaign': MockTraCampaign,
                'TraPrice': MockTraPrice,
                'CorNetwork': MockCorNetwork,
                'TraOrderStatus': MockTraOrderStatus,
                'TraInvoice': MockTraInvoice
            };
            createController = function() {
                $injector.get('$controller')("TraOrderDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'protoneApp:traOrderUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
