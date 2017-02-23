'use strict';

describe('Controller Tests', function() {

    describe('CrmAccount Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCrmAccount, MockCorPerson, MockCorAddress, MockCorNetwork, MockTraDiscount, MockCorUser, MockCorCountry, MockCorRange, MockCorSize, MockTraIndustry, MockCorArea, MockCrmTask;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCrmAccount = jasmine.createSpy('MockCrmAccount');
            MockCorPerson = jasmine.createSpy('MockCorPerson');
            MockCorAddress = jasmine.createSpy('MockCorAddress');
            MockCorNetwork = jasmine.createSpy('MockCorNetwork');
            MockTraDiscount = jasmine.createSpy('MockTraDiscount');
            MockCorUser = jasmine.createSpy('MockCorUser');
            MockCorCountry = jasmine.createSpy('MockCorCountry');
            MockCorRange = jasmine.createSpy('MockCorRange');
            MockCorSize = jasmine.createSpy('MockCorSize');
            MockTraIndustry = jasmine.createSpy('MockTraIndustry');
            MockCorArea = jasmine.createSpy('MockCorArea');
            MockCrmTask = jasmine.createSpy('MockCrmTask');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'CrmAccount': MockCrmAccount,
                'CorPerson': MockCorPerson,
                'CorAddress': MockCorAddress,
                'CorNetwork': MockCorNetwork,
                'TraDiscount': MockTraDiscount,
                'CorUser': MockCorUser,
                'CorCountry': MockCorCountry,
                'CorRange': MockCorRange,
                'CorSize': MockCorSize,
                'TraIndustry': MockTraIndustry,
                'CorArea': MockCorArea,
                'CrmTask': MockCrmTask
            };
            createController = function() {
                $injector.get('$controller')("CrmAccountDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'protoneApp:crmAccountUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
