(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CORTag', CORTag);

    CORTag.$inject = ['$resource'];

    function CORTag ($resource) {
        var resourceUrl =  'api/c-or-tags/:id';

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
