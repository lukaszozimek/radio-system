'use strict';

describe('Controller Tests', function() {

    describe('CorCountry Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCorCountry, MockCorTax, MockCorCurrency, MockCorNetwork;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCorCountry = jasmine.createSpy('MockCorCountry');
            MockCorTax = jasmine.createSpy('MockCorTax');
            MockCorCurrency = jasmine.createSpy('MockCorCurrency');
            MockCorNetwork = jasmine.createSpy('MockCorNetwork');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'CorCountry': MockCorCountry,
                'CorTax': MockCorTax,
                'CorCurrency': MockCorCurrency,
                'CorNetwork': MockCorNetwork
            };
            createController = function() {
                $injector.get('$controller')("CorCountryDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'protoneApp:corCountryUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
