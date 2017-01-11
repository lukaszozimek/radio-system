(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('l-ib-cloud-object', {
            parent: 'entity',
            url: '/l-ib-cloud-object',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.lIBCloudObject.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/l-ib-cloud-object/l-ib-cloud-objects.html',
                    controller: 'LIBCloudObjectController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lIBCloudObject');
                    $translatePartialLoader.addPart('lIBObjectTypeEnum');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('l-ib-cloud-object-detail', {
            parent: 'entity',
            url: '/l-ib-cloud-object/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.lIBCloudObject.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/l-ib-cloud-object/l-ib-cloud-object-detail.html',
                    controller: 'LIBCloudObjectDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lIBCloudObject');
                    $translatePartialLoader.addPart('lIBObjectTypeEnum');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LIBCloudObject', function($stateParams, LIBCloudObject) {
                    return LIBCloudObject.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'l-ib-cloud-object',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('l-ib-cloud-object-detail.edit', {
            parent: 'l-ib-cloud-object-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-cloud-object/l-ib-cloud-object-dialog.html',
                    controller: 'LIBCloudObjectDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LIBCloudObject', function(LIBCloudObject) {
                            return LIBCloudObject.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('l-ib-cloud-object.new', {
            parent: 'l-ib-cloud-object',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-cloud-object/l-ib-cloud-object-dialog.html',
                    controller: 'LIBCloudObjectDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                uuid: null,
                                objectType: null,
                                originalName: null,
                                contentType: null,
                                size: null,
                                original: null,
                                hash: null,
                                createDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('l-ib-cloud-object', null, { reload: 'l-ib-cloud-object' });
                }, function() {
                    $state.go('l-ib-cloud-object');
                });
            }]
        })
        .state('l-ib-cloud-object.edit', {
            parent: 'l-ib-cloud-object',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-cloud-object/l-ib-cloud-object-dialog.html',
                    controller: 'LIBCloudObjectDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LIBCloudObject', function(LIBCloudObject) {
                            return LIBCloudObject.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('l-ib-cloud-object', null, { reload: 'l-ib-cloud-object' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('l-ib-cloud-object.delete', {
            parent: 'l-ib-cloud-object',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-cloud-object/l-ib-cloud-object-delete-dialog.html',
                    controller: 'LIBCloudObjectDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LIBCloudObject', function(LIBCloudObject) {
                            return LIBCloudObject.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('l-ib-cloud-object', null, { reload: 'l-ib-cloud-object' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
