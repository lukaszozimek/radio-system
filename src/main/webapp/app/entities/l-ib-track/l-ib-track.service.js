(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('LIBTrack', LIBTrack);

    LIBTrack.$inject = ['$resource'];

    function LIBTrack ($resource) {
        var resourceUrl =  'api/l-ib-tracks/:id';

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
