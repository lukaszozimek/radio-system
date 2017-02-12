(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cor-range', {
            parent: 'entity',
            url: '/cor-range',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.corRange.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cor-range/cor-ranges.html',
                    controller: 'CorRangeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('corRange');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cor-range-detail', {
            parent: 'cor-range',
            url: '/cor-range/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.corRange.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cor-range/cor-range-detail.html',
                    controller: 'CorRangeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('corRange');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CorRange', function($stateParams, CorRange) {
                    return CorRange.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'cor-range',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('cor-range-detail.edit', {
            parent: 'cor-range-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-range/cor-range-dialog.html',
                    controller: 'CorRangeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CorRange', function(CorRange) {
                            return CorRange.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cor-range.new', {
            parent: 'cor-range',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-range/cor-range-dialog.html',
                    controller: 'CorRangeDialogController',
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
                    $state.go('cor-range', null, { reload: 'cor-range' });
                }, function() {
                    $state.go('cor-range');
                });
            }]
        })
        .state('cor-range.edit', {
            parent: 'cor-range',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-range/cor-range-dialog.html',
                    controller: 'CorRangeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CorRange', function(CorRange) {
                            return CorRange.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cor-range', null, { reload: 'cor-range' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cor-range.delete', {
            parent: 'cor-range',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-range/cor-range-delete-dialog.html',
                    controller: 'CorRangeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CorRange', function(CorRange) {
                            return CorRange.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cor-range', null, { reload: 'cor-range' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
