(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('LibTrack', LibTrack);

    LibTrack.$inject = ['$resource'];

    function LibTrack ($resource) {
        var resourceUrl =  'api/lib-tracks/:id';

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
