(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cor-country', {
            parent: 'entity',
            url: '/cor-country',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.corCountry.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cor-country/cor-countries.html',
                    controller: 'CorCountryController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('corCountry');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cor-country-detail', {
            parent: 'cor-country',
            url: '/cor-country/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.corCountry.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cor-country/cor-country-detail.html',
                    controller: 'CorCountryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('corCountry');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CorCountry', function($stateParams, CorCountry) {
                    return CorCountry.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'cor-country',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('cor-country-detail.edit', {
            parent: 'cor-country-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-country/cor-country-dialog.html',
                    controller: 'CorCountryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CorCountry', function(CorCountry) {
                            return CorCountry.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cor-country.new', {
            parent: 'cor-country',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-country/cor-country-dialog.html',
                    controller: 'CorCountryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                shortName: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('cor-country', null, { reload: 'cor-country' });
                }, function() {
                    $state.go('cor-country');
                });
            }]
        })
        .state('cor-country.edit', {
            parent: 'cor-country',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-country/cor-country-dialog.html',
                    controller: 'CorCountryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CorCountry', function(CorCountry) {
                            return CorCountry.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cor-country', null, { reload: 'cor-country' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cor-country.delete', {
            parent: 'cor-country',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-country/cor-country-delete-dialog.html',
                    controller: 'CorCountryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CorCountry', function(CorCountry) {
                            return CorCountry.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cor-country', null, { reload: 'cor-country' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
