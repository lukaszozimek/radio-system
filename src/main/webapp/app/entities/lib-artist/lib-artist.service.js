(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('LibArtist', LibArtist);

    LibArtist.$inject = ['$resource'];

    function LibArtist ($resource) {
        var resourceUrl =  'api/lib-artists/:id';

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
