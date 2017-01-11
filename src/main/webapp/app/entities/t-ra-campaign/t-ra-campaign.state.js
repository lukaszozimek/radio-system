(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('t-ra-campaign', {
            parent: 'entity',
            url: '/t-ra-campaign',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.tRACampaign.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/t-ra-campaign/t-ra-campaigns.html',
                    controller: 'TRACampaignController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tRACampaign');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('t-ra-campaign-detail', {
            parent: 'entity',
            url: '/t-ra-campaign/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.tRACampaign.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/t-ra-campaign/t-ra-campaign-detail.html',
                    controller: 'TRACampaignDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tRACampaign');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TRACampaign', function($stateParams, TRACampaign) {
                    return TRACampaign.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 't-ra-campaign',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('t-ra-campaign-detail.edit', {
            parent: 't-ra-campaign-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-ra-campaign/t-ra-campaign-dialog.html',
                    controller: 'TRACampaignDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TRACampaign', function(TRACampaign) {
                            return TRACampaign.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('t-ra-campaign.new', {
            parent: 't-ra-campaign',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-ra-campaign/t-ra-campaign-dialog.html',
                    controller: 'TRACampaignDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                startDate: null,
                                endDate: null,
                                prize: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('t-ra-campaign', null, { reload: 't-ra-campaign' });
                }, function() {
                    $state.go('t-ra-campaign');
                });
            }]
        })
        .state('t-ra-campaign.edit', {
            parent: 't-ra-campaign',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-ra-campaign/t-ra-campaign-dialog.html',
                    controller: 'TRACampaignDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TRACampaign', function(TRACampaign) {
                            return TRACampaign.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('t-ra-campaign', null, { reload: 't-ra-campaign' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('t-ra-campaign.delete', {
            parent: 't-ra-campaign',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-ra-campaign/t-ra-campaign-delete-dialog.html',
                    controller: 'TRACampaignDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TRACampaign', function(TRACampaign) {
                            return TRACampaign.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('t-ra-campaign', null, { reload: 't-ra-campaign' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
