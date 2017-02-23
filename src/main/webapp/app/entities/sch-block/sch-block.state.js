(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('sch-block', {
            parent: 'entity',
            url: '/sch-block',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.schBlock.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sch-block/sch-blocks.html',
                    controller: 'SchBlockController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('schBlock');
                    $translatePartialLoader.addPart('schBlockTypeEnum');
                    $translatePartialLoader.addPart('schStartTypeEnum');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('sch-block-detail', {
            parent: 'sch-block',
            url: '/sch-block/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.schBlock.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sch-block/sch-block-detail.html',
                    controller: 'SchBlockDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('schBlock');
                    $translatePartialLoader.addPart('schBlockTypeEnum');
                    $translatePartialLoader.addPart('schStartTypeEnum');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SchBlock', function($stateParams, SchBlock) {
                    return SchBlock.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'sch-block',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('sch-block-detail.edit', {
            parent: 'sch-block-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sch-block/sch-block-dialog.html',
                    controller: 'SchBlockDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SchBlock', function(SchBlock) {
                            return SchBlock.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sch-block.new', {
            parent: 'sch-block',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sch-block/sch-block-dialog.html',
                    controller: 'SchBlockDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                seq: null,
                                name: null,
                                type: null,
                                startType: null,
                                relativeDelay: null,
                                scheduledStartTime: null,
                                scheduledEndTime: null,
                                scheduledLength: null,
                                startTime: null,
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
                    $state.go('sch-block', null, { reload: 'sch-block' });
                }, function() {
                    $state.go('sch-block');
                });
            }]
        })
        .state('sch-block.edit', {
            parent: 'sch-block',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sch-block/sch-block-dialog.html',
                    controller: 'SchBlockDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SchBlock', function(SchBlock) {
                            return SchBlock.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sch-block', null, { reload: 'sch-block' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sch-block.delete', {
            parent: 'sch-block',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sch-block/sch-block-delete-dialog.html',
                    controller: 'SchBlockDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SchBlock', function(SchBlock) {
                            return SchBlock.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sch-block', null, { reload: 'sch-block' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
