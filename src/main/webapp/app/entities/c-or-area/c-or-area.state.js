(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-or-area', {
            parent: 'entity',
            url: '/c-or-area',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cORArea.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-or-area/c-or-areas.html',
                    controller: 'CORAreaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cORArea');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-or-area-detail', {
            parent: 'entity',
            url: '/c-or-area/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cORArea.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-or-area/c-or-area-detail.html',
                    controller: 'CORAreaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cORArea');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CORArea', function($stateParams, CORArea) {
                    return CORArea.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'c-or-area',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('c-or-area-detail.edit', {
            parent: 'c-or-area-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-area/c-or-area-dialog.html',
                    controller: 'CORAreaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CORArea', function(CORArea) {
                            return CORArea.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-or-area.new', {
            parent: 'c-or-area',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-area/c-or-area-dialog.html',
                    controller: 'CORAreaDialogController',
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
                    $state.go('c-or-area', null, { reload: 'c-or-area' });
                }, function() {
                    $state.go('c-or-area');
                });
            }]
        })
        .state('c-or-area.edit', {
            parent: 'c-or-area',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-area/c-or-area-dialog.html',
                    controller: 'CORAreaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CORArea', function(CORArea) {
                            return CORArea.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-or-area', null, { reload: 'c-or-area' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-or-area.delete', {
            parent: 'c-or-area',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-area/c-or-area-delete-dialog.html',
                    controller: 'CORAreaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CORArea', function(CORArea) {
                            return CORArea.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-or-area', null, { reload: 'c-or-area' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
