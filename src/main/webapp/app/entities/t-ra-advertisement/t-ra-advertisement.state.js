(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('t-ra-advertisement', {
            parent: 'entity',
            url: '/t-ra-advertisement',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.tRAAdvertisement.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/t-ra-advertisement/t-ra-advertisements.html',
                    controller: 'TRAAdvertisementController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tRAAdvertisement');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('t-ra-advertisement-detail', {
            parent: 'entity',
            url: '/t-ra-advertisement/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.tRAAdvertisement.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/t-ra-advertisement/t-ra-advertisement-detail.html',
                    controller: 'TRAAdvertisementDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tRAAdvertisement');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TRAAdvertisement', function($stateParams, TRAAdvertisement) {
                    return TRAAdvertisement.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 't-ra-advertisement',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('t-ra-advertisement-detail.edit', {
            parent: 't-ra-advertisement-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-ra-advertisement/t-ra-advertisement-dialog.html',
                    controller: 'TRAAdvertisementDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TRAAdvertisement', function(TRAAdvertisement) {
                            return TRAAdvertisement.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('t-ra-advertisement.new', {
            parent: 't-ra-advertisement',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-ra-advertisement/t-ra-advertisement-dialog.html',
                    controller: 'TRAAdvertisementDialogController',
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
                    $state.go('t-ra-advertisement', null, { reload: 't-ra-advertisement' });
                }, function() {
                    $state.go('t-ra-advertisement');
                });
            }]
        })
        .state('t-ra-advertisement.edit', {
            parent: 't-ra-advertisement',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-ra-advertisement/t-ra-advertisement-dialog.html',
                    controller: 'TRAAdvertisementDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TRAAdvertisement', function(TRAAdvertisement) {
                            return TRAAdvertisement.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('t-ra-advertisement', null, { reload: 't-ra-advertisement' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('t-ra-advertisement.delete', {
            parent: 't-ra-advertisement',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-ra-advertisement/t-ra-advertisement-delete-dialog.html',
                    controller: 'TRAAdvertisementDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TRAAdvertisement', function(TRAAdvertisement) {
                            return TRAAdvertisement.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('t-ra-advertisement', null, { reload: 't-ra-advertisement' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
