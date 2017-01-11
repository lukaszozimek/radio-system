(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('l-ib-audio-object', {
            parent: 'entity',
            url: '/l-ib-audio-object',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.lIBAudioObject.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/l-ib-audio-object/l-ib-audio-objects.html',
                    controller: 'LIBAudioObjectController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lIBAudioObject');
                    $translatePartialLoader.addPart('lIBAudioQualityEnum');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('l-ib-audio-object-detail', {
            parent: 'entity',
            url: '/l-ib-audio-object/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.lIBAudioObject.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/l-ib-audio-object/l-ib-audio-object-detail.html',
                    controller: 'LIBAudioObjectDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lIBAudioObject');
                    $translatePartialLoader.addPart('lIBAudioQualityEnum');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LIBAudioObject', function($stateParams, LIBAudioObject) {
                    return LIBAudioObject.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'l-ib-audio-object',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('l-ib-audio-object-detail.edit', {
            parent: 'l-ib-audio-object-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-audio-object/l-ib-audio-object-dialog.html',
                    controller: 'LIBAudioObjectDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LIBAudioObject', function(LIBAudioObject) {
                            return LIBAudioObject.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('l-ib-audio-object.new', {
            parent: 'l-ib-audio-object',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-audio-object/l-ib-audio-object-dialog.html',
                    controller: 'LIBAudioObjectDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                length: null,
                                bitrate: null,
                                codec: null,
                                quality: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('l-ib-audio-object', null, { reload: 'l-ib-audio-object' });
                }, function() {
                    $state.go('l-ib-audio-object');
                });
            }]
        })
        .state('l-ib-audio-object.edit', {
            parent: 'l-ib-audio-object',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-audio-object/l-ib-audio-object-dialog.html',
                    controller: 'LIBAudioObjectDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LIBAudioObject', function(LIBAudioObject) {
                            return LIBAudioObject.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('l-ib-audio-object', null, { reload: 'l-ib-audio-object' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('l-ib-audio-object.delete', {
            parent: 'l-ib-audio-object',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-audio-object/l-ib-audio-object-delete-dialog.html',
                    controller: 'LIBAudioObjectDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LIBAudioObject', function(LIBAudioObject) {
                            return LIBAudioObject.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('l-ib-audio-object', null, { reload: 'l-ib-audio-object' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
