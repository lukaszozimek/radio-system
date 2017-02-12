(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('LibVideoObject', LibVideoObject);

    LibVideoObject.$inject = ['$resource'];

    function LibVideoObject ($resource) {
        var resourceUrl =  'api/lib-video-objects/:id';

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
