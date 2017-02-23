(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CorNetwork', CorNetwork);

    CorNetwork.$inject = ['$resource'];

    function CorNetwork ($resource) {
        var resourceUrl =  'api/cor-networks/:id';

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
