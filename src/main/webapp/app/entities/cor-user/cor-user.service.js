(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CorUser', CorUser);

    CorUser.$inject = ['$resource'];

    function CorUser ($resource) {
        var resourceUrl =  'api/cor-users/:id';

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
