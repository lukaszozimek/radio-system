(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CorContact', CorContact);

    CorContact.$inject = ['$resource'];

    function CorContact ($resource) {
        var resourceUrl =  'api/cor-contacts/:id';

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
