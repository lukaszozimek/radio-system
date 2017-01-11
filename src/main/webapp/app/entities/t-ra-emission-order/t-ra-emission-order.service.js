(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('TRAEmissionOrder', TRAEmissionOrder);

    TRAEmissionOrder.$inject = ['$resource'];

    function TRAEmissionOrder ($resource) {
        var resourceUrl =  'api/t-ra-emission-orders/:id';

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
