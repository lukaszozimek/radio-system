'use strict';

describe('Controller Tests', function() {

    describe('CfgMarkerConfiguration Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCfgMarkerConfiguration, MockCorNetwork;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCfgMarkerConfiguration = jasmine.createSpy('MockCfgMarkerConfiguration');
            MockCorNetwork = jasmine.createSpy('MockCorNetwork');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'CfgMarkerConfiguration': MockCfgMarkerConfiguration,
                'CorNetwork': MockCorNetwork
            };
            createController = function() {
                $injector.get('$controller')("CfgMarkerConfigurationDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'protoneApp:cfgMarkerConfigurationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
