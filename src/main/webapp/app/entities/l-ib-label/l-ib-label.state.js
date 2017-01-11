(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('l-ib-label', {
            parent: 'entity',
            url: '/l-ib-label',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.lIBLabel.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/l-ib-label/l-ib-labels.html',
                    controller: 'LIBLabelController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lIBLabel');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('l-ib-label-detail', {
            parent: 'entity',
            url: '/l-ib-label/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.lIBLabel.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/l-ib-label/l-ib-label-detail.html',
                    controller: 'LIBLabelDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lIBLabel');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LIBLabel', function($stateParams, LIBLabel) {
                    return LIBLabel.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'l-ib-label',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('l-ib-label-detail.edit', {
            parent: 'l-ib-label-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-label/l-ib-label-dialog.html',
                    controller: 'LIBLabelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LIBLabel', function(LIBLabel) {
                            return LIBLabel.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('l-ib-label.new', {
            parent: 'l-ib-label',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-label/l-ib-label-dialog.html',
                    controller: 'LIBLabelDialogController',
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
                    $state.go('l-ib-label', null, { reload: 'l-ib-label' });
                }, function() {
                    $state.go('l-ib-label');
                });
            }]
        })
        .state('l-ib-label.edit', {
            parent: 'l-ib-label',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-label/l-ib-label-dialog.html',
                    controller: 'LIBLabelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LIBLabel', function(LIBLabel) {
                            return LIBLabel.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('l-ib-label', null, { reload: 'l-ib-label' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('l-ib-label.delete', {
            parent: 'l-ib-label',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-label/l-ib-label-delete-dialog.html',
                    controller: 'LIBLabelDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LIBLabel', function(LIBLabel) {
                            return LIBLabel.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('l-ib-label', null, { reload: 'l-ib-label' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
