(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cor-address', {
            parent: 'entity',
            url: '/cor-address',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.corAddress.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cor-address/cor-addresses.html',
                    controller: 'CorAddressController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('corAddress');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cor-address-detail', {
            parent: 'cor-address',
            url: '/cor-address/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.corAddress.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cor-address/cor-address-detail.html',
                    controller: 'CorAddressDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('corAddress');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CorAddress', function($stateParams, CorAddress) {
                    return CorAddress.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'cor-address',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('cor-address-detail.edit', {
            parent: 'cor-address-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-address/cor-address-dialog.html',
                    controller: 'CorAddressDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CorAddress', function(CorAddress) {
                            return CorAddress.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cor-address.new', {
            parent: 'cor-address',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-address/cor-address-dialog.html',
                    controller: 'CorAddressDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                street: null,
                                number: null,
                                postalCode: null,
                                city: null,
                                country: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('cor-address', null, { reload: 'cor-address' });
                }, function() {
                    $state.go('cor-address');
                });
            }]
        })
        .state('cor-address.edit', {
            parent: 'cor-address',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-address/cor-address-dialog.html',
                    controller: 'CorAddressDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CorAddress', function(CorAddress) {
                            return CorAddress.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cor-address', null, { reload: 'cor-address' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cor-address.delete', {
            parent: 'cor-address',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-address/cor-address-delete-dialog.html',
                    controller: 'CorAddressDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CorAddress', function(CorAddress) {
                            return CorAddress.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cor-address', null, { reload: 'cor-address' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
