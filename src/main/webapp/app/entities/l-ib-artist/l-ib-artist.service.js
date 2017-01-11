(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('LIBArtist', LIBArtist);

    LIBArtist.$inject = ['$resource'];

    function LIBArtist ($resource) {
        var resourceUrl =  'api/l-ib-artists/:id';

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
