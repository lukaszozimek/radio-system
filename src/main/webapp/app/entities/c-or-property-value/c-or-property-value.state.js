(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-or-property-value', {
            parent: 'entity',
            url: '/c-or-property-value',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cORPropertyValue.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-or-property-value/c-or-property-values.html',
                    controller: 'CORPropertyValueController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cORPropertyValue');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-or-property-value-detail', {
            parent: 'entity',
            url: '/c-or-property-value/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cORPropertyValue.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-or-property-value/c-or-property-value-detail.html',
                    controller: 'CORPropertyValueDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cORPropertyValue');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CORPropertyValue', function($stateParams, CORPropertyValue) {
                    return CORPropertyValue.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'c-or-property-value',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('c-or-property-value-detail.edit', {
            parent: 'c-or-property-value-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-property-value/c-or-property-value-dialog.html',
                    controller: 'CORPropertyValueDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CORPropertyValue', function(CORPropertyValue) {
                            return CORPropertyValue.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-or-property-value.new', {
            parent: 'c-or-property-value',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-property-value/c-or-property-value-dialog.html',
                    controller: 'CORPropertyValueDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                value: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('c-or-property-value', null, { reload: 'c-or-property-value' });
                }, function() {
                    $state.go('c-or-property-value');
                });
            }]
        })
        .state('c-or-property-value.edit', {
            parent: 'c-or-property-value',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-property-value/c-or-property-value-dialog.html',
                    controller: 'CORPropertyValueDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CORPropertyValue', function(CORPropertyValue) {
                            return CORPropertyValue.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-or-property-value', null, { reload: 'c-or-property-value' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-or-property-value.delete', {
            parent: 'c-or-property-value',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-property-value/c-or-property-value-delete-dialog.html',
                    controller: 'CORPropertyValueDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CORPropertyValue', function(CORPropertyValue) {
                            return CORPropertyValue.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-or-property-value', null, { reload: 'c-or-property-value' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
