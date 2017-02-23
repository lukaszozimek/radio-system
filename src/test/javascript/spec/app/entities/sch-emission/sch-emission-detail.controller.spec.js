'use strict';

describe('Controller Tests', function() {

    describe('SchEmission Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockSchEmission, MockLibMediaItem, MockSchBlock, MockTraCampaign;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockSchEmission = jasmine.createSpy('MockSchEmission');
            MockLibMediaItem = jasmine.createSpy('MockLibMediaItem');
            MockSchBlock = jasmine.createSpy('MockSchBlock');
            MockTraCampaign = jasmine.createSpy('MockTraCampaign');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'SchEmission': MockSchEmission,
                'LibMediaItem': MockLibMediaItem,
                'SchBlock': MockSchBlock,
                'TraCampaign': MockTraCampaign
            };
            createController = function() {
                $injector.get('$controller')("SchEmissionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'protoneApp:schEmissionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
