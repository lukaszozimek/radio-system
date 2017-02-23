(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('lib-cloud-object', {
            parent: 'entity',
            url: '/lib-cloud-object',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.libCloudObject.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lib-cloud-object/lib-cloud-objects.html',
                    controller: 'LibCloudObjectController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('libCloudObject');
                    $translatePartialLoader.addPart('libObjectTypeEnum');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('lib-cloud-object-detail', {
            parent: 'lib-cloud-object',
            url: '/lib-cloud-object/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.libCloudObject.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lib-cloud-object/lib-cloud-object-detail.html',
                    controller: 'LibCloudObjectDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('libCloudObject');
                    $translatePartialLoader.addPart('libObjectTypeEnum');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LibCloudObject', function($stateParams, LibCloudObject) {
                    return LibCloudObject.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'lib-cloud-object',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('lib-cloud-object-detail.edit', {
            parent: 'lib-cloud-object-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-cloud-object/lib-cloud-object-dialog.html',
                    controller: 'LibCloudObjectDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LibCloudObject', function(LibCloudObject) {
                            return LibCloudObject.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lib-cloud-object.new', {
            parent: 'lib-cloud-object',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-cloud-object/lib-cloud-object-dialog.html',
                    controller: 'LibCloudObjectDialogController',
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
                    $state.go('lib-cloud-object', null, { reload: 'lib-cloud-object' });
                }, function() {
                    $state.go('lib-cloud-object');
                });
            }]
        })
        .state('lib-cloud-object.edit', {
            parent: 'lib-cloud-object',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-cloud-object/lib-cloud-object-dialog.html',
                    controller: 'LibCloudObjectDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LibCloudObject', function(LibCloudObject) {
                            return LibCloudObject.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lib-cloud-object', null, { reload: 'lib-cloud-object' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lib-cloud-object.delete', {
            parent: 'lib-cloud-object',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-cloud-object/lib-cloud-object-delete-dialog.html',
                    controller: 'LibCloudObjectDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LibCloudObject', function(LibCloudObject) {
                            return LibCloudObject.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lib-cloud-object', null, { reload: 'lib-cloud-object' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
