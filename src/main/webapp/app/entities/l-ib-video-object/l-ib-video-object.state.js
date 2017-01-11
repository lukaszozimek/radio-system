(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('l-ib-video-object', {
            parent: 'entity',
            url: '/l-ib-video-object',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.lIBVideoObject.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/l-ib-video-object/l-ib-video-objects.html',
                    controller: 'LIBVideoObjectController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lIBVideoObject');
                    $translatePartialLoader.addPart('lIBVideoQualityEnum');
                    $translatePartialLoader.addPart('lIBAspectRatioEnum');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('l-ib-video-object-detail', {
            parent: 'entity',
            url: '/l-ib-video-object/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.lIBVideoObject.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/l-ib-video-object/l-ib-video-object-detail.html',
                    controller: 'LIBVideoObjectDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lIBVideoObject');
                    $translatePartialLoader.addPart('lIBVideoQualityEnum');
                    $translatePartialLoader.addPart('lIBAspectRatioEnum');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LIBVideoObject', function($stateParams, LIBVideoObject) {
                    return LIBVideoObject.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'l-ib-video-object',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('l-ib-video-object-detail.edit', {
            parent: 'l-ib-video-object-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-video-object/l-ib-video-object-dialog.html',
                    controller: 'LIBVideoObjectDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LIBVideoObject', function(LIBVideoObject) {
                            return LIBVideoObject.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('l-ib-video-object.new', {
            parent: 'l-ib-video-object',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-video-object/l-ib-video-object-dialog.html',
                    controller: 'LIBVideoObjectDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                width: null,
                                height: null,
                                length: null,
                                bitrate: null,
                                codec: null,
                                quality: null,
                                aspectRatio: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('l-ib-video-object', null, { reload: 'l-ib-video-object' });
                }, function() {
                    $state.go('l-ib-video-object');
                });
            }]
        })
        .state('l-ib-video-object.edit', {
            parent: 'l-ib-video-object',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-video-object/l-ib-video-object-dialog.html',
                    controller: 'LIBVideoObjectDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LIBVideoObject', function(LIBVideoObject) {
                            return LIBVideoObject.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('l-ib-video-object', null, { reload: 'l-ib-video-object' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('l-ib-video-object.delete', {
            parent: 'l-ib-video-object',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-video-object/l-ib-video-object-delete-dialog.html',
                    controller: 'LIBVideoObjectDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LIBVideoObject', function(LIBVideoObject) {
                            return LIBVideoObject.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('l-ib-video-object', null, { reload: 'l-ib-video-object' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
