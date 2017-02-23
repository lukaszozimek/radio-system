'use strict';

describe('Controller Tests', function() {

    describe('CorContact Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCorContact, MockCorNetwork, MockCorPerson;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCorContact = jasmine.createSpy('MockCorContact');
            MockCorNetwork = jasmine.createSpy('MockCorNetwork');
            MockCorPerson = jasmine.createSpy('MockCorPerson');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'CorContact': MockCorContact,
                'CorNetwork': MockCorNetwork,
                'CorPerson': MockCorPerson
            };
            createController = function() {
                $injector.get('$controller')("CorContactDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'protoneApp:corContactUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
