'use strict';

describe('Controller Tests', function() {

    describe('CorUser Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCorUser, MockCorNetwork, MockCorChannel;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCorUser = jasmine.createSpy('MockCorUser');
            MockCorNetwork = jasmine.createSpy('MockCorNetwork');
            MockCorChannel = jasmine.createSpy('MockCorChannel');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'CorUser': MockCorUser,
                'CorNetwork': MockCorNetwork,
                'CorChannel': MockCorChannel
            };
            createController = function() {
                $injector.get('$controller')("CorUserDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'protoneApp:corUserUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
