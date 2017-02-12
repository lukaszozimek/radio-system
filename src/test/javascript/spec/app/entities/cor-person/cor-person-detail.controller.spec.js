'use strict';

describe('Controller Tests', function() {

    describe('CorPerson Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCorPerson, MockCorNetwork, MockCorContact;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCorPerson = jasmine.createSpy('MockCorPerson');
            MockCorNetwork = jasmine.createSpy('MockCorNetwork');
            MockCorContact = jasmine.createSpy('MockCorContact');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'CorPerson': MockCorPerson,
                'CorNetwork': MockCorNetwork,
                'CorContact': MockCorContact
            };
            createController = function() {
                $injector.get('$controller')("CorPersonDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'protoneApp:corPersonUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
