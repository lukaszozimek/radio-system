(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('lib-track', {
            parent: 'entity',
            url: '/lib-track',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.libTrack.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lib-track/lib-tracks.html',
                    controller: 'LibTrackController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('libTrack');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('lib-track-detail', {
            parent: 'lib-track',
            url: '/lib-track/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.libTrack.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lib-track/lib-track-detail.html',
                    controller: 'LibTrackDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('libTrack');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LibTrack', function($stateParams, LibTrack) {
                    return LibTrack.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'lib-track',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('lib-track-detail.edit', {
            parent: 'lib-track-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-track/lib-track-dialog.html',
                    controller: 'LibTrackDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LibTrack', function(LibTrack) {
                            return LibTrack.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lib-track.new', {
            parent: 'lib-track',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-track/lib-track-dialog.html',
                    controller: 'LibTrackDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                discNo: null,
                                trackNo: null,
                                name: null,
                                length: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('lib-track', null, { reload: 'lib-track' });
                }, function() {
                    $state.go('lib-track');
                });
            }]
        })
        .state('lib-track.edit', {
            parent: 'lib-track',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-track/lib-track-dialog.html',
                    controller: 'LibTrackDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LibTrack', function(LibTrack) {
                            return LibTrack.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lib-track', null, { reload: 'lib-track' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lib-track.delete', {
            parent: 'lib-track',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-track/lib-track-delete-dialog.html',
                    controller: 'LibTrackDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LibTrack', function(LibTrack) {
                            return LibTrack.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lib-track', null, { reload: 'lib-track' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
