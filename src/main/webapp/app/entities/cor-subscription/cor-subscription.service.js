(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CorSubscription', CorSubscription);

    CorSubscription.$inject = ['$resource'];

    function CorSubscription ($resource) {
        var resourceUrl =  'api/cor-subscriptions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
