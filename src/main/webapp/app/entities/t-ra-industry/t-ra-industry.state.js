(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('t-ra-industry', {
            parent: 'entity',
            url: '/t-ra-industry',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.tRAIndustry.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/t-ra-industry/t-ra-industries.html',
                    controller: 'TRAIndustryController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tRAIndustry');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('t-ra-industry-detail', {
            parent: 'entity',
            url: '/t-ra-industry/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.tRAIndustry.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/t-ra-industry/t-ra-industry-detail.html',
                    controller: 'TRAIndustryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tRAIndustry');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TRAIndustry', function($stateParams, TRAIndustry) {
                    return TRAIndustry.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 't-ra-industry',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('t-ra-industry-detail.edit', {
            parent: 't-ra-industry-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-ra-industry/t-ra-industry-dialog.html',
                    controller: 'TRAIndustryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TRAIndustry', function(TRAIndustry) {
                            return TRAIndustry.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('t-ra-industry.new', {
            parent: 't-ra-industry',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-ra-industry/t-ra-industry-dialog.html',
                    controller: 'TRAIndustryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('t-ra-industry', null, { reload: 't-ra-industry' });
                }, function() {
                    $state.go('t-ra-industry');
                });
            }]
        })
        .state('t-ra-industry.edit', {
            parent: 't-ra-industry',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-ra-industry/t-ra-industry-dialog.html',
                    controller: 'TRAIndustryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TRAIndustry', function(TRAIndustry) {
                            return TRAIndustry.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('t-ra-industry', null, { reload: 't-ra-industry' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('t-ra-industry.delete', {
            parent: 't-ra-industry',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-ra-industry/t-ra-industry-delete-dialog.html',
                    controller: 'TRAIndustryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TRAIndustry', function(TRAIndustry) {
                            return TRAIndustry.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('t-ra-industry', null, { reload: 't-ra-industry' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
