(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-or-range', {
            parent: 'entity',
            url: '/c-or-range',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cORRange.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-or-range/c-or-ranges.html',
                    controller: 'CORRangeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cORRange');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-or-range-detail', {
            parent: 'entity',
            url: '/c-or-range/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cORRange.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-or-range/c-or-range-detail.html',
                    controller: 'CORRangeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cORRange');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CORRange', function($stateParams, CORRange) {
                    return CORRange.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'c-or-range',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('c-or-range-detail.edit', {
            parent: 'c-or-range-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-range/c-or-range-dialog.html',
                    controller: 'CORRangeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CORRange', function(CORRange) {
                            return CORRange.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-or-range.new', {
            parent: 'c-or-range',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-range/c-or-range-dialog.html',
                    controller: 'CORRangeDialogController',
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
                    $state.go('c-or-range', null, { reload: 'c-or-range' });
                }, function() {
                    $state.go('c-or-range');
                });
            }]
        })
        .state('c-or-range.edit', {
            parent: 'c-or-range',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-range/c-or-range-dialog.html',
                    controller: 'CORRangeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CORRange', function(CORRange) {
                            return CORRange.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-or-range', null, { reload: 'c-or-range' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-or-range.delete', {
            parent: 'c-or-range',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-range/c-or-range-delete-dialog.html',
                    controller: 'CORRangeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CORRange', function(CORRange) {
                            return CORRange.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-or-range', null, { reload: 'c-or-range' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
