(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-or-channel', {
            parent: 'entity',
            url: '/c-or-channel',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cORChannel.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-or-channel/c-or-channels.html',
                    controller: 'CORChannelController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cORChannel');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-or-channel-detail', {
            parent: 'entity',
            url: '/c-or-channel/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cORChannel.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-or-channel/c-or-channel-detail.html',
                    controller: 'CORChannelDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cORChannel');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CORChannel', function($stateParams, CORChannel) {
                    return CORChannel.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'c-or-channel',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('c-or-channel-detail.edit', {
            parent: 'c-or-channel-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-channel/c-or-channel-dialog.html',
                    controller: 'CORChannelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CORChannel', function(CORChannel) {
                            return CORChannel.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-or-channel.new', {
            parent: 'c-or-channel',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-channel/c-or-channel-dialog.html',
                    controller: 'CORChannelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                shortcut: null,
                                name: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('c-or-channel', null, { reload: 'c-or-channel' });
                }, function() {
                    $state.go('c-or-channel');
                });
            }]
        })
        .state('c-or-channel.edit', {
            parent: 'c-or-channel',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-channel/c-or-channel-dialog.html',
                    controller: 'CORChannelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CORChannel', function(CORChannel) {
                            return CORChannel.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-or-channel', null, { reload: 'c-or-channel' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-or-channel.delete', {
            parent: 'c-or-channel',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-channel/c-or-channel-delete-dialog.html',
                    controller: 'CORChannelDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CORChannel', function(CORChannel) {
                            return CORChannel.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-or-channel', null, { reload: 'c-or-channel' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
