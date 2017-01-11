(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-or-address', {
            parent: 'entity',
            url: '/c-or-address',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cORAddress.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-or-address/c-or-addresses.html',
                    controller: 'CORAddressController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cORAddress');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-or-address-detail', {
            parent: 'entity',
            url: '/c-or-address/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cORAddress.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-or-address/c-or-address-detail.html',
                    controller: 'CORAddressDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cORAddress');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CORAddress', function($stateParams, CORAddress) {
                    return CORAddress.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'c-or-address',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('c-or-address-detail.edit', {
            parent: 'c-or-address-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-address/c-or-address-dialog.html',
                    controller: 'CORAddressDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CORAddress', function(CORAddress) {
                            return CORAddress.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-or-address.new', {
            parent: 'c-or-address',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-address/c-or-address-dialog.html',
                    controller: 'CORAddressDialogController',
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
                    $state.go('c-or-address', null, { reload: 'c-or-address' });
                }, function() {
                    $state.go('c-or-address');
                });
            }]
        })
        .state('c-or-address.edit', {
            parent: 'c-or-address',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-address/c-or-address-dialog.html',
                    controller: 'CORAddressDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CORAddress', function(CORAddress) {
                            return CORAddress.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-or-address', null, { reload: 'c-or-address' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-or-address.delete', {
            parent: 'c-or-address',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-address/c-or-address-delete-dialog.html',
                    controller: 'CORAddressDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CORAddress', function(CORAddress) {
                            return CORAddress.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-or-address', null, { reload: 'c-or-address' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
