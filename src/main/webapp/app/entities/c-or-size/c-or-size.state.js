(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-or-size', {
            parent: 'entity',
            url: '/c-or-size',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cORSize.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-or-size/c-or-sizes.html',
                    controller: 'CORSizeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cORSize');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-or-size-detail', {
            parent: 'entity',
            url: '/c-or-size/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cORSize.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-or-size/c-or-size-detail.html',
                    controller: 'CORSizeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cORSize');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CORSize', function($stateParams, CORSize) {
                    return CORSize.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'c-or-size',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('c-or-size-detail.edit', {
            parent: 'c-or-size-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-size/c-or-size-dialog.html',
                    controller: 'CORSizeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CORSize', function(CORSize) {
                            return CORSize.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-or-size.new', {
            parent: 'c-or-size',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-size/c-or-size-dialog.html',
                    controller: 'CORSizeDialogController',
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
                    $state.go('c-or-size', null, { reload: 'c-or-size' });
                }, function() {
                    $state.go('c-or-size');
                });
            }]
        })
        .state('c-or-size.edit', {
            parent: 'c-or-size',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-size/c-or-size-dialog.html',
                    controller: 'CORSizeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CORSize', function(CORSize) {
                            return CORSize.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-or-size', null, { reload: 'c-or-size' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-or-size.delete', {
            parent: 'c-or-size',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-size/c-or-size-delete-dialog.html',
                    controller: 'CORSizeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CORSize', function(CORSize) {
                            return CORSize.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-or-size', null, { reload: 'c-or-size' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
