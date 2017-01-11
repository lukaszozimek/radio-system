(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('TRABlockPrice', TRABlockPrice);

    TRABlockPrice.$inject = ['$resource'];

    function TRABlockPrice ($resource) {
        var resourceUrl =  'api/t-ra-block-prices/:id';

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
