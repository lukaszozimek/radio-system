(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('l-ib-artist', {
            parent: 'entity',
            url: '/l-ib-artist',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.lIBArtist.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/l-ib-artist/l-ib-artists.html',
                    controller: 'LIBArtistController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lIBArtist');
                    $translatePartialLoader.addPart('lIBArtistTypeEnum');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('l-ib-artist-detail', {
            parent: 'entity',
            url: '/l-ib-artist/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.lIBArtist.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/l-ib-artist/l-ib-artist-detail.html',
                    controller: 'LIBArtistDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lIBArtist');
                    $translatePartialLoader.addPart('lIBArtistTypeEnum');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LIBArtist', function($stateParams, LIBArtist) {
                    return LIBArtist.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'l-ib-artist',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('l-ib-artist-detail.edit', {
            parent: 'l-ib-artist-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-artist/l-ib-artist-dialog.html',
                    controller: 'LIBArtistDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LIBArtist', function(LIBArtist) {
                            return LIBArtist.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('l-ib-artist.new', {
            parent: 'l-ib-artist',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-artist/l-ib-artist-dialog.html',
                    controller: 'LIBArtistDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                type: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('l-ib-artist', null, { reload: 'l-ib-artist' });
                }, function() {
                    $state.go('l-ib-artist');
                });
            }]
        })
        .state('l-ib-artist.edit', {
            parent: 'l-ib-artist',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-artist/l-ib-artist-dialog.html',
                    controller: 'LIBArtistDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LIBArtist', function(LIBArtist) {
                            return LIBArtist.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('l-ib-artist', null, { reload: 'l-ib-artist' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('l-ib-artist.delete', {
            parent: 'l-ib-artist',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-artist/l-ib-artist-delete-dialog.html',
                    controller: 'LIBArtistDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LIBArtist', function(LIBArtist) {
                            return LIBArtist.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('l-ib-artist', null, { reload: 'l-ib-artist' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
