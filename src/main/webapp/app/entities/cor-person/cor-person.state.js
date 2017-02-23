(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cor-person', {
            parent: 'entity',
            url: '/cor-person',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.corPerson.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cor-person/cor-people.html',
                    controller: 'CorPersonController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('corPerson');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cor-person-detail', {
            parent: 'cor-person',
            url: '/cor-person/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.corPerson.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cor-person/cor-person-detail.html',
                    controller: 'CorPersonDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('corPerson');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CorPerson', function($stateParams, CorPerson) {
                    return CorPerson.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'cor-person',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('cor-person-detail.edit', {
            parent: 'cor-person-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-person/cor-person-dialog.html',
                    controller: 'CorPersonDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CorPerson', function(CorPerson) {
                            return CorPerson.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cor-person.new', {
            parent: 'cor-person',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-person/cor-person-dialog.html',
                    controller: 'CorPersonDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                firstName: null,
                                lastName: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('cor-person', null, { reload: 'cor-person' });
                }, function() {
                    $state.go('cor-person');
                });
            }]
        })
        .state('cor-person.edit', {
            parent: 'cor-person',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-person/cor-person-dialog.html',
                    controller: 'CorPersonDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CorPerson', function(CorPerson) {
                            return CorPerson.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cor-person', null, { reload: 'cor-person' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cor-person.delete', {
            parent: 'cor-person',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-person/cor-person-delete-dialog.html',
                    controller: 'CorPersonDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CorPerson', function(CorPerson) {
                            return CorPerson.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cor-person', null, { reload: 'cor-person' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
