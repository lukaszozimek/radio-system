(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CorTag', CorTag);

    CorTag.$inject = ['$resource'];

    function CorTag ($resource) {
        var resourceUrl =  'api/cor-tags/:id';

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
