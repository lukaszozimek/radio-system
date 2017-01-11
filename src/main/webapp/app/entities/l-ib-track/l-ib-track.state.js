(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('l-ib-track', {
            parent: 'entity',
            url: '/l-ib-track',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.lIBTrack.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/l-ib-track/l-ib-tracks.html',
                    controller: 'LIBTrackController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lIBTrack');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('l-ib-track-detail', {
            parent: 'entity',
            url: '/l-ib-track/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.lIBTrack.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/l-ib-track/l-ib-track-detail.html',
                    controller: 'LIBTrackDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lIBTrack');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LIBTrack', function($stateParams, LIBTrack) {
                    return LIBTrack.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'l-ib-track',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('l-ib-track-detail.edit', {
            parent: 'l-ib-track-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-track/l-ib-track-dialog.html',
                    controller: 'LIBTrackDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LIBTrack', function(LIBTrack) {
                            return LIBTrack.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('l-ib-track.new', {
            parent: 'l-ib-track',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-track/l-ib-track-dialog.html',
                    controller: 'LIBTrackDialogController',
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
                    $state.go('l-ib-track', null, { reload: 'l-ib-track' });
                }, function() {
                    $state.go('l-ib-track');
                });
            }]
        })
        .state('l-ib-track.edit', {
            parent: 'l-ib-track',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-track/l-ib-track-dialog.html',
                    controller: 'LIBTrackDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LIBTrack', function(LIBTrack) {
                            return LIBTrack.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('l-ib-track', null, { reload: 'l-ib-track' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('l-ib-track.delete', {
            parent: 'l-ib-track',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-track/l-ib-track-delete-dialog.html',
                    controller: 'LIBTrackDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LIBTrack', function(LIBTrack) {
                            return LIBTrack.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('l-ib-track', null, { reload: 'l-ib-track' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
