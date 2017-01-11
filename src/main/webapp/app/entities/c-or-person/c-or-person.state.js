(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-or-person', {
            parent: 'entity',
            url: '/c-or-person',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cORPerson.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-or-person/c-or-people.html',
                    controller: 'CORPersonController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cORPerson');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-or-person-detail', {
            parent: 'entity',
            url: '/c-or-person/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cORPerson.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-or-person/c-or-person-detail.html',
                    controller: 'CORPersonDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cORPerson');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CORPerson', function($stateParams, CORPerson) {
                    return CORPerson.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'c-or-person',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('c-or-person-detail.edit', {
            parent: 'c-or-person-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-person/c-or-person-dialog.html',
                    controller: 'CORPersonDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CORPerson', function(CORPerson) {
                            return CORPerson.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-or-person.new', {
            parent: 'c-or-person',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-person/c-or-person-dialog.html',
                    controller: 'CORPersonDialogController',
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
                    $state.go('c-or-person', null, { reload: 'c-or-person' });
                }, function() {
                    $state.go('c-or-person');
                });
            }]
        })
        .state('c-or-person.edit', {
            parent: 'c-or-person',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-person/c-or-person-dialog.html',
                    controller: 'CORPersonDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CORPerson', function(CORPerson) {
                            return CORPerson.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-or-person', null, { reload: 'c-or-person' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-or-person.delete', {
            parent: 'c-or-person',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-person/c-or-person-delete-dialog.html',
                    controller: 'CORPersonDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CORPerson', function(CORPerson) {
                            return CORPerson.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-or-person', null, { reload: 'c-or-person' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
