(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-or-tag', {
            parent: 'entity',
            url: '/c-or-tag',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cORTag.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-or-tag/c-or-tags.html',
                    controller: 'CORTagController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cORTag');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-or-tag-detail', {
            parent: 'entity',
            url: '/c-or-tag/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cORTag.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-or-tag/c-or-tag-detail.html',
                    controller: 'CORTagDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cORTag');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CORTag', function($stateParams, CORTag) {
                    return CORTag.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'c-or-tag',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('c-or-tag-detail.edit', {
            parent: 'c-or-tag-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-tag/c-or-tag-dialog.html',
                    controller: 'CORTagDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CORTag', function(CORTag) {
                            return CORTag.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-or-tag.new', {
            parent: 'c-or-tag',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-tag/c-or-tag-dialog.html',
                    controller: 'CORTagDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                tag: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('c-or-tag', null, { reload: 'c-or-tag' });
                }, function() {
                    $state.go('c-or-tag');
                });
            }]
        })
        .state('c-or-tag.edit', {
            parent: 'c-or-tag',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-tag/c-or-tag-dialog.html',
                    controller: 'CORTagDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CORTag', function(CORTag) {
                            return CORTag.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-or-tag', null, { reload: 'c-or-tag' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-or-tag.delete', {
            parent: 'c-or-tag',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-tag/c-or-tag-delete-dialog.html',
                    controller: 'CORTagDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CORTag', function(CORTag) {
                            return CORTag.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-or-tag', null, { reload: 'c-or-tag' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
