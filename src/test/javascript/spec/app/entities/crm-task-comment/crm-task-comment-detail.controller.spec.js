'use strict';

describe('Controller Tests', function() {

    describe('CrmTaskComment Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCrmTaskComment, MockCrmTask, MockCorUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCrmTaskComment = jasmine.createSpy('MockCrmTaskComment');
            MockCrmTask = jasmine.createSpy('MockCrmTask');
            MockCorUser = jasmine.createSpy('MockCorUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'CrmTaskComment': MockCrmTaskComment,
                'CrmTask': MockCrmTask,
                'CorUser': MockCorUser
            };
            createController = function() {
                $injector.get('$controller')("CrmTaskCommentDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'protoneApp:crmTaskCommentUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
