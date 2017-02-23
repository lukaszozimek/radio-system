(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tra-advertisment-type', {
            parent: 'entity',
            url: '/tra-advertisment-type',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.traAdvertismentType.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tra-advertisment-type/tra-advertisment-types.html',
                    controller: 'TraAdvertismentTypeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('traAdvertismentType');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tra-advertisment-type-detail', {
            parent: 'tra-advertisment-type',
            url: '/tra-advertisment-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.traAdvertismentType.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tra-advertisment-type/tra-advertisment-type-detail.html',
                    controller: 'TraAdvertismentTypeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('traAdvertismentType');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TraAdvertismentType', function($stateParams, TraAdvertismentType) {
                    return TraAdvertismentType.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tra-advertisment-type',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tra-advertisment-type-detail.edit', {
            parent: 'tra-advertisment-type-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-advertisment-type/tra-advertisment-type-dialog.html',
                    controller: 'TraAdvertismentTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TraAdvertismentType', function(TraAdvertismentType) {
                            return TraAdvertismentType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tra-advertisment-type.new', {
            parent: 'tra-advertisment-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-advertisment-type/tra-advertisment-type-dialog.html',
                    controller: 'TraAdvertismentTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tra-advertisment-type', null, { reload: 'tra-advertisment-type' });
                }, function() {
                    $state.go('tra-advertisment-type');
                });
            }]
        })
        .state('tra-advertisment-type.edit', {
            parent: 'tra-advertisment-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-advertisment-type/tra-advertisment-type-dialog.html',
                    controller: 'TraAdvertismentTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TraAdvertismentType', function(TraAdvertismentType) {
                            return TraAdvertismentType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tra-advertisment-type', null, { reload: 'tra-advertisment-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tra-advertisment-type.delete', {
            parent: 'tra-advertisment-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-advertisment-type/tra-advertisment-type-delete-dialog.html',
                    controller: 'TraAdvertismentTypeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TraAdvertismentType', function(TraAdvertismentType) {
                            return TraAdvertismentType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tra-advertisment-type', null, { reload: 'tra-advertisment-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
