(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CorArea', CorArea);

    CorArea.$inject = ['$resource'];

    function CorArea ($resource) {
        var resourceUrl =  'api/cor-areas/:id';

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
