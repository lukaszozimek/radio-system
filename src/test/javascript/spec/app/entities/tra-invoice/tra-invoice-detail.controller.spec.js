'use strict';

describe('Controller Tests', function() {

    describe('TraInvoice Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTraInvoice, MockCrmAccount, MockCorNetwork, MockTraInvoiceStatus, MockTraOrder;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTraInvoice = jasmine.createSpy('MockTraInvoice');
            MockCrmAccount = jasmine.createSpy('MockCrmAccount');
            MockCorNetwork = jasmine.createSpy('MockCorNetwork');
            MockTraInvoiceStatus = jasmine.createSpy('MockTraInvoiceStatus');
            MockTraOrder = jasmine.createSpy('MockTraOrder');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'TraInvoice': MockTraInvoice,
                'CrmAccount': MockCrmAccount,
                'CorNetwork': MockCorNetwork,
                'TraInvoiceStatus': MockTraInvoiceStatus,
                'TraOrder': MockTraOrder
            };
            createController = function() {
                $injector.get('$controller')("TraInvoiceDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'protoneApp:traInvoiceUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
