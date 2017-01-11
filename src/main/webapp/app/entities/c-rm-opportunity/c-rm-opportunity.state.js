(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-rm-opportunity', {
            parent: 'entity',
            url: '/c-rm-opportunity',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cRMOpportunity.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-rm-opportunity/c-rm-opportunities.html',
                    controller: 'CRMOpportunityController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cRMOpportunity');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-rm-opportunity-detail', {
            parent: 'entity',
            url: '/c-rm-opportunity/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cRMOpportunity.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-rm-opportunity/c-rm-opportunity-detail.html',
                    controller: 'CRMOpportunityDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cRMOpportunity');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CRMOpportunity', function($stateParams, CRMOpportunity) {
                    return CRMOpportunity.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'c-rm-opportunity',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('c-rm-opportunity-detail.edit', {
            parent: 'c-rm-opportunity-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-rm-opportunity/c-rm-opportunity-dialog.html',
                    controller: 'CRMOpportunityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CRMOpportunity', function(CRMOpportunity) {
                            return CRMOpportunity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-rm-opportunity.new', {
            parent: 'c-rm-opportunity',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-rm-opportunity/c-rm-opportunity-dialog.html',
                    controller: 'CRMOpportunityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                lastTry: null,
                                closeDate: null,
                                probability: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('c-rm-opportunity', null, { reload: 'c-rm-opportunity' });
                }, function() {
                    $state.go('c-rm-opportunity');
                });
            }]
        })
        .state('c-rm-opportunity.edit', {
            parent: 'c-rm-opportunity',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-rm-opportunity/c-rm-opportunity-dialog.html',
                    controller: 'CRMOpportunityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CRMOpportunity', function(CRMOpportunity) {
                            return CRMOpportunity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-rm-opportunity', null, { reload: 'c-rm-opportunity' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-rm-opportunity.delete', {
            parent: 'c-rm-opportunity',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-rm-opportunity/c-rm-opportunity-delete-dialog.html',
                    controller: 'CRMOpportunityDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CRMOpportunity', function(CRMOpportunity) {
                            return CRMOpportunity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-rm-opportunity', null, { reload: 'c-rm-opportunity' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
