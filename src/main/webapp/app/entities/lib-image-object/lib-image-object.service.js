(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('LibImageObject', LibImageObject);

    LibImageObject.$inject = ['$resource'];

    function LibImageObject ($resource) {
        var resourceUrl =  'api/lib-image-objects/:id';

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
