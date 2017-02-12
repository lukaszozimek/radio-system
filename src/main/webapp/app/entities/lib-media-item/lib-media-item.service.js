(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('LibMediaItem', LibMediaItem);

    LibMediaItem.$inject = ['$resource'];

    function LibMediaItem ($resource) {
        var resourceUrl =  'api/lib-media-items/:id';

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
