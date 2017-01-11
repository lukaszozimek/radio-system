(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-rm-stage', {
            parent: 'entity',
            url: '/c-rm-stage',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cRMStage.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-rm-stage/c-rm-stages.html',
                    controller: 'CRMStageController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cRMStage');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-rm-stage-detail', {
            parent: 'entity',
            url: '/c-rm-stage/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cRMStage.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-rm-stage/c-rm-stage-detail.html',
                    controller: 'CRMStageDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cRMStage');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CRMStage', function($stateParams, CRMStage) {
                    return CRMStage.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'c-rm-stage',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('c-rm-stage-detail.edit', {
            parent: 'c-rm-stage-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-rm-stage/c-rm-stage-dialog.html',
                    controller: 'CRMStageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CRMStage', function(CRMStage) {
                            return CRMStage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-rm-stage.new', {
            parent: 'c-rm-stage',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-rm-stage/c-rm-stage-dialog.html',
                    controller: 'CRMStageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('c-rm-stage', null, { reload: 'c-rm-stage' });
                }, function() {
                    $state.go('c-rm-stage');
                });
            }]
        })
        .state('c-rm-stage.edit', {
            parent: 'c-rm-stage',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-rm-stage/c-rm-stage-dialog.html',
                    controller: 'CRMStageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CRMStage', function(CRMStage) {
                            return CRMStage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-rm-stage', null, { reload: 'c-rm-stage' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-rm-stage.delete', {
            parent: 'c-rm-stage',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-rm-stage/c-rm-stage-delete-dialog.html',
                    controller: 'CRMStageDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CRMStage', function(CRMStage) {
                            return CRMStage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-rm-stage', null, { reload: 'c-rm-stage' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
