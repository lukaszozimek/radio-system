(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('TraAdvertisement', TraAdvertisement);

    TraAdvertisement.$inject = ['$resource'];

    function TraAdvertisement ($resource) {
        var resourceUrl =  'api/tra-advertisements/:id';

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
