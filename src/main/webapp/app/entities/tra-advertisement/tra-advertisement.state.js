(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tra-advertisement', {
            parent: 'entity',
            url: '/tra-advertisement',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.traAdvertisement.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tra-advertisement/tra-advertisements.html',
                    controller: 'TraAdvertisementController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('traAdvertisement');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tra-advertisement-detail', {
            parent: 'tra-advertisement',
            url: '/tra-advertisement/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.traAdvertisement.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tra-advertisement/tra-advertisement-detail.html',
                    controller: 'TraAdvertisementDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('traAdvertisement');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TraAdvertisement', function($stateParams, TraAdvertisement) {
                    return TraAdvertisement.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tra-advertisement',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tra-advertisement-detail.edit', {
            parent: 'tra-advertisement-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-advertisement/tra-advertisement-dialog.html',
                    controller: 'TraAdvertisementDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TraAdvertisement', function(TraAdvertisement) {
                            return TraAdvertisement.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tra-advertisement.new', {
            parent: 'tra-advertisement',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-advertisement/tra-advertisement-dialog.html',
                    controller: 'TraAdvertisementDialogController',
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
                    $state.go('tra-advertisement', null, { reload: 'tra-advertisement' });
                }, function() {
                    $state.go('tra-advertisement');
                });
            }]
        })
        .state('tra-advertisement.edit', {
            parent: 'tra-advertisement',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-advertisement/tra-advertisement-dialog.html',
                    controller: 'TraAdvertisementDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TraAdvertisement', function(TraAdvertisement) {
                            return TraAdvertisement.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tra-advertisement', null, { reload: 'tra-advertisement' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tra-advertisement.delete', {
            parent: 'tra-advertisement',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-advertisement/tra-advertisement-delete-dialog.html',
                    controller: 'TraAdvertisementDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TraAdvertisement', function(TraAdvertisement) {
                            return TraAdvertisement.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tra-advertisement', null, { reload: 'tra-advertisement' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
