(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('s-ch-block', {
            parent: 'entity',
            url: '/s-ch-block',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.sCHBlock.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/s-ch-block/s-ch-blocks.html',
                    controller: 'SCHBlockController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('sCHBlock');
                    $translatePartialLoader.addPart('sCHBlockTypeEnum');
                    $translatePartialLoader.addPart('sCHStartTypeEnum');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('s-ch-block-detail', {
            parent: 'entity',
            url: '/s-ch-block/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.sCHBlock.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/s-ch-block/s-ch-block-detail.html',
                    controller: 'SCHBlockDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('sCHBlock');
                    $translatePartialLoader.addPart('sCHBlockTypeEnum');
                    $translatePartialLoader.addPart('sCHStartTypeEnum');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SCHBlock', function($stateParams, SCHBlock) {
                    return SCHBlock.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 's-ch-block',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('s-ch-block-detail.edit', {
            parent: 's-ch-block-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/s-ch-block/s-ch-block-dialog.html',
                    controller: 'SCHBlockDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SCHBlock', function(SCHBlock) {
                            return SCHBlock.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('s-ch-block.new', {
            parent: 's-ch-block',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/s-ch-block/s-ch-block-dialog.html',
                    controller: 'SCHBlockDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                seq: null,
                                name: null,
                                type: null,
                                startTime: null,
                                startType: null,
                                relativeDelay: null,
                                endTime: null,
                                length: null,
                                dimYear: null,
                                dimMonth: null,
                                dimDay: null,
                                dimHour: null,
                                dimMinute: null,
                                dimSecond: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('s-ch-block', null, { reload: 's-ch-block' });
                }, function() {
                    $state.go('s-ch-block');
                });
            }]
        })
        .state('s-ch-block.edit', {
            parent: 's-ch-block',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/s-ch-block/s-ch-block-dialog.html',
                    controller: 'SCHBlockDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SCHBlock', function(SCHBlock) {
                            return SCHBlock.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('s-ch-block', null, { reload: 's-ch-block' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('s-ch-block.delete', {
            parent: 's-ch-block',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/s-ch-block/s-ch-block-delete-dialog.html',
                    controller: 'SCHBlockDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SCHBlock', function(SCHBlock) {
                            return SCHBlock.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('s-ch-block', null, { reload: 's-ch-block' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
