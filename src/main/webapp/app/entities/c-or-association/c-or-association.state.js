(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-or-association', {
            parent: 'entity',
            url: '/c-or-association',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cORAssociation.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-or-association/c-or-associations.html',
                    controller: 'CORAssociationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cORAssociation');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-or-association-detail', {
            parent: 'entity',
            url: '/c-or-association/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cORAssociation.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-or-association/c-or-association-detail.html',
                    controller: 'CORAssociationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cORAssociation');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CORAssociation', function($stateParams, CORAssociation) {
                    return CORAssociation.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'c-or-association',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('c-or-association-detail.edit', {
            parent: 'c-or-association-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-association/c-or-association-dialog.html',
                    controller: 'CORAssociationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CORAssociation', function(CORAssociation) {
                            return CORAssociation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-or-association.new', {
            parent: 'c-or-association',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-association/c-or-association-dialog.html',
                    controller: 'CORAssociationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                sourceClass: null,
                                sourceId: null,
                                targetClass: null,
                                targetId: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('c-or-association', null, { reload: 'c-or-association' });
                }, function() {
                    $state.go('c-or-association');
                });
            }]
        })
        .state('c-or-association.edit', {
            parent: 'c-or-association',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-association/c-or-association-dialog.html',
                    controller: 'CORAssociationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CORAssociation', function(CORAssociation) {
                            return CORAssociation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-or-association', null, { reload: 'c-or-association' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-or-association.delete', {
            parent: 'c-or-association',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-association/c-or-association-delete-dialog.html',
                    controller: 'CORAssociationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CORAssociation', function(CORAssociation) {
                            return CORAssociation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-or-association', null, { reload: 'c-or-association' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
