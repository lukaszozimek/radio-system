(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('crm-stage', {
            parent: 'entity',
            url: '/crm-stage',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.crmStage.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/crm-stage/crm-stages.html',
                    controller: 'CrmStageController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('crmStage');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('crm-stage-detail', {
            parent: 'crm-stage',
            url: '/crm-stage/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.crmStage.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/crm-stage/crm-stage-detail.html',
                    controller: 'CrmStageDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('crmStage');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CrmStage', function($stateParams, CrmStage) {
                    return CrmStage.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'crm-stage',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('crm-stage-detail.edit', {
            parent: 'crm-stage-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-stage/crm-stage-dialog.html',
                    controller: 'CrmStageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CrmStage', function(CrmStage) {
                            return CrmStage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('crm-stage.new', {
            parent: 'crm-stage',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-stage/crm-stage-dialog.html',
                    controller: 'CrmStageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('crm-stage', null, { reload: 'crm-stage' });
                }, function() {
                    $state.go('crm-stage');
                });
            }]
        })
        .state('crm-stage.edit', {
            parent: 'crm-stage',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-stage/crm-stage-dialog.html',
                    controller: 'CrmStageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CrmStage', function(CrmStage) {
                            return CrmStage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('crm-stage', null, { reload: 'crm-stage' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('crm-stage.delete', {
            parent: 'crm-stage',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-stage/crm-stage-delete-dialog.html',
                    controller: 'CrmStageDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CrmStage', function(CrmStage) {
                            return CrmStage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('crm-stage', null, { reload: 'crm-stage' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
