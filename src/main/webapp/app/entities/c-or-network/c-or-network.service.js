(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CORNetwork', CORNetwork);

    CORNetwork.$inject = ['$resource'];

    function CORNetwork ($resource) {
        var resourceUrl =  'api/c-or-networks/:id';

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
