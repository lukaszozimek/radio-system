(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cor-tag', {
            parent: 'entity',
            url: '/cor-tag',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.corTag.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cor-tag/cor-tags.html',
                    controller: 'CorTagController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('corTag');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cor-tag-detail', {
            parent: 'cor-tag',
            url: '/cor-tag/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.corTag.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cor-tag/cor-tag-detail.html',
                    controller: 'CorTagDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('corTag');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CorTag', function($stateParams, CorTag) {
                    return CorTag.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'cor-tag',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('cor-tag-detail.edit', {
            parent: 'cor-tag-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-tag/cor-tag-dialog.html',
                    controller: 'CorTagDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CorTag', function(CorTag) {
                            return CorTag.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cor-tag.new', {
            parent: 'cor-tag',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-tag/cor-tag-dialog.html',
                    controller: 'CorTagDialogController',
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
                    $state.go('cor-tag', null, { reload: 'cor-tag' });
                }, function() {
                    $state.go('cor-tag');
                });
            }]
        })
        .state('cor-tag.edit', {
            parent: 'cor-tag',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-tag/cor-tag-dialog.html',
                    controller: 'CorTagDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CorTag', function(CorTag) {
                            return CorTag.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cor-tag', null, { reload: 'cor-tag' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cor-tag.delete', {
            parent: 'cor-tag',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-tag/cor-tag-delete-dialog.html',
                    controller: 'CorTagDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CorTag', function(CorTag) {
                            return CorTag.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cor-tag', null, { reload: 'cor-tag' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
