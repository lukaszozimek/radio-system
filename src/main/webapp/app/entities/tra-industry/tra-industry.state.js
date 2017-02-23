(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tra-industry', {
            parent: 'entity',
            url: '/tra-industry',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.traIndustry.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tra-industry/tra-industries.html',
                    controller: 'TraIndustryController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('traIndustry');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tra-industry-detail', {
            parent: 'tra-industry',
            url: '/tra-industry/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.traIndustry.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tra-industry/tra-industry-detail.html',
                    controller: 'TraIndustryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('traIndustry');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TraIndustry', function($stateParams, TraIndustry) {
                    return TraIndustry.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tra-industry',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tra-industry-detail.edit', {
            parent: 'tra-industry-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-industry/tra-industry-dialog.html',
                    controller: 'TraIndustryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TraIndustry', function(TraIndustry) {
                            return TraIndustry.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tra-industry.new', {
            parent: 'tra-industry',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-industry/tra-industry-dialog.html',
                    controller: 'TraIndustryDialogController',
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
                    $state.go('tra-industry', null, { reload: 'tra-industry' });
                }, function() {
                    $state.go('tra-industry');
                });
            }]
        })
        .state('tra-industry.edit', {
            parent: 'tra-industry',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-industry/tra-industry-dialog.html',
                    controller: 'TraIndustryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TraIndustry', function(TraIndustry) {
                            return TraIndustry.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tra-industry', null, { reload: 'tra-industry' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tra-industry.delete', {
            parent: 'tra-industry',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-industry/tra-industry-delete-dialog.html',
                    controller: 'TraIndustryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TraIndustry', function(TraIndustry) {
                            return TraIndustry.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tra-industry', null, { reload: 'tra-industry' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
