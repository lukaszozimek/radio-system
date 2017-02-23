(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('lib-artist', {
            parent: 'entity',
            url: '/lib-artist',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.libArtist.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lib-artist/lib-artists.html',
                    controller: 'LibArtistController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('libArtist');
                    $translatePartialLoader.addPart('libArtistTypeEnum');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('lib-artist-detail', {
            parent: 'lib-artist',
            url: '/lib-artist/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.libArtist.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lib-artist/lib-artist-detail.html',
                    controller: 'LibArtistDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('libArtist');
                    $translatePartialLoader.addPart('libArtistTypeEnum');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LibArtist', function($stateParams, LibArtist) {
                    return LibArtist.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'lib-artist',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('lib-artist-detail.edit', {
            parent: 'lib-artist-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-artist/lib-artist-dialog.html',
                    controller: 'LibArtistDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LibArtist', function(LibArtist) {
                            return LibArtist.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lib-artist.new', {
            parent: 'lib-artist',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-artist/lib-artist-dialog.html',
                    controller: 'LibArtistDialogController',
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
                    $state.go('lib-artist', null, { reload: 'lib-artist' });
                }, function() {
                    $state.go('lib-artist');
                });
            }]
        })
        .state('lib-artist.edit', {
            parent: 'lib-artist',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-artist/lib-artist-dialog.html',
                    controller: 'LibArtistDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LibArtist', function(LibArtist) {
                            return LibArtist.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lib-artist', null, { reload: 'lib-artist' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lib-artist.delete', {
            parent: 'lib-artist',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-artist/lib-artist-delete-dialog.html',
                    controller: 'LibArtistDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LibArtist', function(LibArtist) {
                            return LibArtist.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lib-artist', null, { reload: 'lib-artist' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
