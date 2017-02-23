'use strict';

describe('Controller Tests', function() {

    describe('LibCloudObject Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockLibCloudObject, MockCorNetwork, MockCorUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockLibCloudObject = jasmine.createSpy('MockLibCloudObject');
            MockCorNetwork = jasmine.createSpy('MockCorNetwork');
            MockCorUser = jasmine.createSpy('MockCorUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'LibCloudObject': MockLibCloudObject,
                'CorNetwork': MockCorNetwork,
                'CorUser': MockCorUser
            };
            createController = function() {
                $injector.get('$controller')("LibCloudObjectDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'protoneApp:libCloudObjectUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
