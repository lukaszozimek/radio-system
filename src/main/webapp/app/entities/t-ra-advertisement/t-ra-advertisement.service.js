(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('TRAAdvertisement', TRAAdvertisement);

    TRAAdvertisement.$inject = ['$resource'];

    function TRAAdvertisement ($resource) {
        var resourceUrl =  'api/t-ra-advertisements/:id';

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
