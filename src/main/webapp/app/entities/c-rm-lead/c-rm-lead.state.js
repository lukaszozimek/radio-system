(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-rm-lead', {
            parent: 'entity',
            url: '/c-rm-lead',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cRMLead.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-rm-lead/c-rm-leads.html',
                    controller: 'CRMLeadController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cRMLead');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-rm-lead-detail', {
            parent: 'entity',
            url: '/c-rm-lead/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cRMLead.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-rm-lead/c-rm-lead-detail.html',
                    controller: 'CRMLeadDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cRMLead');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CRMLead', function($stateParams, CRMLead) {
                    return CRMLead.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'c-rm-lead',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('c-rm-lead-detail.edit', {
            parent: 'c-rm-lead-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-rm-lead/c-rm-lead-dialog.html',
                    controller: 'CRMLeadDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CRMLead', function(CRMLead) {
                            return CRMLead.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-rm-lead.new', {
            parent: 'c-rm-lead',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-rm-lead/c-rm-lead-dialog.html',
                    controller: 'CRMLeadDialogController',
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
                    $state.go('c-rm-lead', null, { reload: 'c-rm-lead' });
                }, function() {
                    $state.go('c-rm-lead');
                });
            }]
        })
        .state('c-rm-lead.edit', {
            parent: 'c-rm-lead',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-rm-lead/c-rm-lead-dialog.html',
                    controller: 'CRMLeadDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CRMLead', function(CRMLead) {
                            return CRMLead.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-rm-lead', null, { reload: 'c-rm-lead' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-rm-lead.delete', {
            parent: 'c-rm-lead',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-rm-lead/c-rm-lead-delete-dialog.html',
                    controller: 'CRMLeadDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CRMLead', function(CRMLead) {
                            return CRMLead.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-rm-lead', null, { reload: 'c-rm-lead' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
