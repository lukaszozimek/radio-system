(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CorCurrency', CorCurrency);

    CorCurrency.$inject = ['$resource'];

    function CorCurrency ($resource) {
        var resourceUrl =  'api/cor-currencies/:id';

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
